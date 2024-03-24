package xd.jg.custom_figures.work_manager

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class GlbDownloadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val fileUrl = inputData.getString("fileUrl") ?: return Result.failure()
        val fileName = inputData.getString("fileName") ?: return Result.failure()

        try {
            val url = URL(fileUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStream = connection.inputStream
            val outputFile = File(applicationContext.filesDir, "$fileName.glb")
            val outputStream = FileOutputStream(outputFile)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()
            inputStream.close()
            val outputData = workDataOf("filePath" to outputFile.absolutePath)
            Log.d("CRINGE_FILES", outputFile.absolutePath)

            return Result.success(outputData)
        } catch (e: Exception) {
            return Result.failure()
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
}