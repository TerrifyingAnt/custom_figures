package xd.jg.custom_figures.work_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.ResponseBody
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@HiltWorker
class GlbDownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iFigureRepository: IFigureRepository
):
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val fileUrl = inputData.getString("fileUrl") ?: return Result.failure()
        val fileName = inputData.getString("fileName") ?: return Result.failure()

        return downloadFile(fileUrl, fileName)
    }

    private suspend fun downloadFile(fileUrl: String, fileName: String): Result {
        val response = iFigureRepository.getModel(fileUrl)
        return if (response.status == Resource.Status.SUCCESS) {
            if (response.data != null) {
                val filePath = saveFile(response.data, fileName)
                Result.success(workDataOf("filePath" to filePath))
            } else {
                Result.failure()
            }
        } else {
            Result.failure()
        }
    }

    companion object {
        fun enqueueDownload(fileUrl: String, fileName: String, applicationContext: Context): UUID{
            val workRequest = OneTimeWorkRequestBuilder<GlbDownloadWorker>()
                .setInputData(
                    workDataOf(
                        "fileUrl" to fileUrl,
                        "fileName" to fileName
                    )
                )
                .build()

            WorkManager.getInstance(applicationContext).enqueue(workRequest)
            return workRequest.id
        }
    }

    private suspend fun saveFile(body: ResponseBody, fileName: String): String {
        val outputFile = File(applicationContext.filesDir, fileName)
        val outputStream = FileOutputStream(outputFile)
        val totalBytes = body.contentLength()
        var downloadedBytes = 0L
        val inputStream = body.byteStream()

        val buffer = ByteArray(8192)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
            downloadedBytes += bytesRead.toLong()
            setProgress(workDataOf("progress" to (downloadedBytes.toFloat() / totalBytes.toFloat())))
        }

        outputStream.flush()
        outputStream.close()
        return outputFile.absolutePath
    }

}