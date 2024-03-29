package xd.jg.custom_figures.presentation.main_screen

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.data.local.DataStoreManager
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.work_manager.GlbDownloadWorker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val iFigureRepository: IFigureRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    init{
        checkIfPhotoWasMade()
    }

    private val _launchModelOnStartup = MutableStateFlow<Resource<Boolean>>(Resource.loading(null))
    val launchModelOnStartup: StateFlow<Resource<Boolean>> = _launchModelOnStartup

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

    private val _skyBoxUserScreen = MutableStateFlow<Resource<Color>>(Resource.loading(Color(255, 255, 255, 255)))
    val skyBoxUserScreen: StateFlow<Resource<Color>> = _skyBoxUserScreen



    // метод сохранения и отправки фотографии
    fun savePhoto(context: Context, applicationContext: Context, bitmap: Bitmap) = viewModelScope.launch {

        val imageFile = File(context.cacheDir, System.currentTimeMillis().toString())
        imageFile.createNewFile()
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        val fos = FileOutputStream(imageFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        updateUIState {
            copy(photoWasMade = true, photoUri = imageFile.absolutePath, isLoading = true)
        }
        dataStoreManager.setPhotoWasMade(true)

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

    private fun checkIfPhotoWasMade() = viewModelScope.launch {
        val photoWasMade = dataStoreManager.getPhotoWasMade().first() ?: false
        _launchModelOnStartup.value = Resource.success(photoWasMade)
    }

    fun getPaths(applicationContext: Context): String {
        return applicationContext.filesDir.absolutePath
    }

    // метод запуска воркеров и отслеживания состояния скачивания
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
        val workRequestId = GlbDownloadWorker.enqueueDownload(fileUrl, fileName, applicationContext)
        val workInfoFlow = WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequestId)
            .asFlow()
            .map { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val outputData = workInfo.outputData
                    val filePath = outputData.getString("filePath")
                    val errorMessage = outputData.getString("errorMessage")
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
                            _downloadStateFigure.value = Resource.success(filePath)
                        }
                        Resource.success(filePath)
                    }else if (errorMessage != null) {
                        Resource.error(errorMessage)
                    } else {
                        Resource.error("Unknown error")
                    }
                } else if (workInfo != null && workInfo.state == WorkInfo.State.RUNNING) {
                    val progress = workInfo.progress.getFloat("progress", 0f)
                    modelState.value = Resource.loading(progress.toString())
                    Resource.loading(progress.toString())
                } else {
                    modelState.value
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), modelState.value)



        viewModelScope.launch {
            workInfoFlow.collect {state ->
                modelState.value = state
            }
        }
    }

    fun changeSkyBoxColor(color: Color) = viewModelScope.launch {
        dataStoreManager.setCurrentColor(color)
        _skyBoxUserScreen.value = Resource.success(color)
    }



}