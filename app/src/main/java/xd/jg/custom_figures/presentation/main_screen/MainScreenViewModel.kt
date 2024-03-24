package xd.jg.custom_figures.presentation.main_screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun savePhoto(context: Context, bitmap: Bitmap) = viewModelScope.launch {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            LocalDateTime.now().toString(),
            null
        )
        val imageFile = getFileFromUri(context, path.toUri()) ?: return@launch
        updateUIState {
            copy(photoWasMade = true, photoUri = path, isLoading = true)
        }
        val response = iFigureRepository.sendImage(imageFile)
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
            downloadGlbFile(response.data.eye, "eye", _downloadStateEyes)
            downloadGlbFile(response.data.hair, "hair", _downloadStateHair)
            downloadGlbFile(response.data.body, "body", _downloadStateBody)
        }


    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile("temp", null, context.cacheDir).apply {
            inputStream?.use { input ->
                FileOutputStream(this).use { output ->
                    input.copyTo(output)
                }
            }
        }
        return if (file.exists()) file else null
    }

    private fun downloadGlbFile(fileUrl: String, fileName: String, modelState: MutableStateFlow<Resource<String>>) {
        modelState.value = Resource.loading(null)

        val workRequestId = GlbDownloadWorker.enqueueDownload(fileUrl, fileName)
        val workInfoFlow = WorkManager.getInstance().getWorkInfoByIdLiveData(workRequestId)
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