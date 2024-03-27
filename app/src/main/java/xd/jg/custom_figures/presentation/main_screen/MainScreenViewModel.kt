package xd.jg.custom_figures.presentation.main_screen

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.work_manager.GlbDownloadWorker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val iFigureRepository: IFigureRepository
): ViewModel() {

    private val _mainScreenUIState = mutableStateOf(MainScreenUIState())
    val mainScreenUIState: State<MainScreenUIState> = _mainScreenUIState

    private fun updateUIState(update: MainScreenUIState.() -> MainScreenUIState) {
        _mainScreenUIState.value = _mainScreenUIState.value.update()
    }

    private val _downloadStateEyes = MutableStateFlow<Resource<String>>(Resource.loading(null))
    val downloadStateEyes: StateFlow<Resource<String>> = _downloadStateEyes

    private val _downloadStateHair = MutableStateFlow<Resource<String>>(Resource.loading(null))
    val downloadStateHair: StateFlow<Resource<String>> = _downloadStateHair

    private val _downloadStateBody = MutableStateFlow<Resource<String>>(Resource.loading(null))
    val downloadStateBody: StateFlow<Resource<String>> = _downloadStateBody

    private val _downloadStateFigure = MutableStateFlow<Resource<String>>(Resource.loading(null))
    val downloadStateFigure: StateFlow<Resource<String>> = _downloadStateFigure



    fun savePhoto(context: Context, applicationContext: Context, bitmap: Bitmap) = viewModelScope.launch {

        val imageFile = File(context.cacheDir, System.currentTimeMillis().toString());
        imageFile.createNewFile();
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray();

        val fos = FileOutputStream(imageFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();

        updateUIState {
            copy(photoWasMade = true, photoUri = imageFile.absolutePath, isLoading = true)
        }
        val response = iFigureRepository.sendImage(imageFile)
        imageFile.delete()
        Log.d("CRINGE_TESTS", response.data.toString() + " " + response.message)
        if (response.data != null) {
            updateUIState {
                copy(
                    modelPartsList = ModelPartListDto(
                        body = response.data.body,
                        eye = response.data.eye,
                        hair = response.data.hair
                    )
                )
            }
            downloadGlbFile(applicationContext, response.data.eye, "eye", _downloadStateEyes)
            downloadGlbFile(applicationContext, response.data.hair, "hair", _downloadStateHair)
            downloadGlbFile(applicationContext, response.data.body, "body", _downloadStateBody)
        }


    }

    private fun downloadGlbFile(applicationContext: Context, fileUrl: String, fileName: String, modelState: MutableStateFlow<Resource<String>>) {
        modelState.value = Resource.loading(null)

        val data = workDataOf(
            "fileUrl" to fileUrl,
            "fileName" to fileName
        )
        val workRequest = OneTimeWorkRequestBuilder<GlbDownloadWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
        val workRequestId = GlbDownloadWorker.enqueueDownload(fileUrl, fileName)
        val workInfoFlow = WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequestId)
            .asFlow()
            .map { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val outputData = workInfo.outputData
                    val filePath = outputData.getString("filePath")
                    val errorMessage = outputData.getString("errorMessage")
                    Log.d("CRINGE_PATH", filePath.toString())
                    if (filePath != null) {
                        updateUIState {
                            if (fileName == "hair") {
                                copy (
                                    hair = filePath
                                )
                            }
                            else {
                                if (fileName == "body") {
                                    copy (
                                        body = filePath
                                    )
                                }
                                else {
                                    copy(
                                        eyes = filePath
                                    )
                                }
                            }
                        }
                        if (_mainScreenUIState.value.body != "" &&
                            _mainScreenUIState.value.hair != "" &&
                            _mainScreenUIState.value.eyes != "") {
                            _downloadStateFigure.value = Resource.success("xd")
                        }
                        Resource.success(filePath)
                    } else if (errorMessage != null) {
                        Resource.error(errorMessage)
                    } else {
                        Resource.error("Unknown error")
                    }
                } else {
                    modelState.value
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), modelState.value)



        viewModelScope.launch {
            workInfoFlow.collect { state ->
                modelState.value = state
            }
        }
    }

    fun changeState() = viewModelScope.launch {
        _downloadStateFigure.value = Resource.loading(null)
    }

}