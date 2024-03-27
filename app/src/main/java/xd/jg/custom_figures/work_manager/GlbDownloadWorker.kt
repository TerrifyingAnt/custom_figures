package xd.jg.custom_figures.work_manager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID
import javax.inject.Inject

@HiltWorker
class GlbDownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iFigureRepository: IFigureRepository
):
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val fileUrl = inputData.getString("fileUrl") ?: return Result.failure()
        val fileName = inputData.getString("fileName") ?: return Result.failure()

        return runBlocking {downloadFile(fileUrl, fileName)}
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
        fun enqueueDownload(fileUrl: String, fileName: String): UUID{
            val workRequest = OneTimeWorkRequestBuilder<GlbDownloadWorker>()
                .setInputData(
                    workDataOf(
                        "fileUrl" to fileUrl,
                        "fileName" to fileName
                    )
                )
                .build()

            WorkManager.getInstance().enqueue(workRequest)
            return workRequest.id
        }
    }

    private fun saveFile(body: ResponseBody, fileName: String): String {
        val outputFile = File(applicationContext.filesDir, fileName)
        val outputStream = FileOutputStream(outputFile)
        outputStream.use { stream ->
            val inputStream = body.byteStream()
            inputStream.copyTo(stream)
        }
        outputStream.close()
        return outputFile.absolutePath
    }
}