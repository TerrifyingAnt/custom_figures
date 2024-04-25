package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.data.local.DataStoreManager
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.domain.local.IBasketRepository
import xd.jg.custom_figures.domain.remote.IPhotoConstructorRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.work_manager.GlbDownloadWorker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@HiltViewModel
class ModelFromPhotoConstructorViewModel @Inject constructor(
    private val iPhotoConstructorRepository: IPhotoConstructorRepository,
    private val dataStoreManager: DataStoreManager,
    private val db: IBasketRepository
): ViewModel() {

    private val _modelFromPhotoConstructorUIState = mutableStateOf(ModelFromPhotoConstructorUIState())
    val modelFromPhotoConstructorUIState: State<ModelFromPhotoConstructorUIState> = _modelFromPhotoConstructorUIState

    /** состояние всего*/
    private fun updateUIState(update: ModelFromPhotoConstructorUIState.() -> ModelFromPhotoConstructorUIState) {
        _modelFromPhotoConstructorUIState.value = _modelFromPhotoConstructorUIState.value.update()
    }



    /** метод сохранения и отправки фотографии */
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
            copy(
                deleteModel = mutableStateOf(false),
                figure = Resource.success(null),
                photoWasMade = mutableStateOf(true),
                photoUri = mutableStateOf(imageFile.absolutePath)
            )
        }
        dataStoreManager.setPhotoWasMade(true)

        val response = iPhotoConstructorRepository.sendImage(imageFile)
        imageFile.delete()
        if (response.data != null) {
            updateUIState {
                copy(
                    modelPartsList = mutableStateOf(ModelPartListDto(
                        body = response.data.body,
                        eye = response.data.eye,
                        hair = response.data.hair
                        )
                    )
                )
            }

            downloadGlbFile(applicationContext, response.data.eye, "eye", _modelFromPhotoConstructorUIState.value.eyes)
            downloadGlbFile(applicationContext, response.data.hair, "hair", _modelFromPhotoConstructorUIState.value.hair)
            downloadGlbFile(applicationContext, response.data.body, "body", _modelFromPhotoConstructorUIState.value.body)
        }
    }

    fun checkIfPhotoWasMade(context: Context) = viewModelScope.launch {
        var photoWasMade = false
        val currentPath = getPaths(context)
        val body = File("$currentPath/body")
        val hair = File("$currentPath/hair")
        val eyes = File("$currentPath/eye")
        if (body.exists() && hair.exists() && eyes.exists()) {
            getCount()
            photoWasMade = true
            updateUIState {
                copy (
                    body = Resource.success(body.absolutePath),
                    hair = Resource.success(hair.absolutePath),
                    eyes = Resource.success(eyes.absolutePath)
                )
            }
        }
        if (photoWasMade) {
            updateUIState {
                copy(
                    photoWasMade = mutableStateOf(photoWasMade),
                    figure = Resource.success(photoWasMade)
                )
            }

        }
        else {
            updateUIState {
                copy(
                    photoWasMade = mutableStateOf(photoWasMade),
                    figure = Resource.loading(photoWasMade)
                )

            }
        }
    }

    fun getPaths(applicationContext: Context): String {
        return applicationContext.filesDir.absolutePath
    }

    private fun downloadGlbFile(applicationContext: Context, fileUrl: String, fileName: String, modelState: Resource<String>) {
        updateProgressCoolByName(fileName, null)

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
                                    hair = Resource.success(filePath)
                                )
                            }
                            else {
                                if (fileName == "body") {
                                    copy (
                                        body = Resource.success(filePath)
                                    )
                                }
                                else {
                                    copy(
                                        eyes = Resource.success(filePath)
                                    )
                                }
                            }
                        }
                        if (_modelFromPhotoConstructorUIState.value.body.status == Resource.Status.SUCCESS &&
                            _modelFromPhotoConstructorUIState.value.hair.status == Resource.Status.SUCCESS &&
                            _modelFromPhotoConstructorUIState.value.eyes.status == Resource.Status.SUCCESS) {
                            updateUIState {
                                copy(
                                    figure = Resource.success(true),
                                )
                            }
                        }
                        Resource.success(filePath)
                    }else if (errorMessage != null) {
                        Resource.error(errorMessage)
                    } else {
                        Resource.error("Unknown error")
                    }
                } else if (workInfo != null && workInfo.state == WorkInfo.State.RUNNING) {
                    val progress = workInfo.progress.getFloat("progress", 0f)
                    updateProgressCoolByName(fileName, progress.toString())
                    Resource.loading(progress.toString())
                } else {
                    val modelName = if (fileName == "hair") "hair" else if (fileName == "body") "body" else "eye"
                    Resource.error("xd", modelName)
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), modelState)



        viewModelScope.launch {
            workInfoFlow.collect {state ->
                if (state.status == Resource.Status.LOADING) {
                    updateProgressCoolByName(fileName, state.data.toString())
                }
                else {
                    updateCoolByName(fileName, state)
                }
            }
        }
    }

    fun deleteModel() {
        updateUIState {
            copy(
                deleteModel = mutableStateOf(true),
                count = mutableIntStateOf(0),
                currentFigureId = mutableIntStateOf(0)
            )
        }
    }

    fun clearScene(context: Context) {
        val currentPath = getPaths(context)
        val body = File("$currentPath/body")
        val hair = File("$currentPath/hair")
        val eyes = File("$currentPath/eye")
        if (body.exists()) {
            body.delete()
        }
        if (hair.exists()) {
            hair.delete()
        }
        if (eyes.exists()) {
            eyes.delete()
        }
        updateUIState {
            copy(
                figure = Resource.loading(null),
                body = Resource.loading(),
                hair = Resource.loading(),
                eyes = Resource.loading(),
                photoWasMade = mutableStateOf(false),
                photoUri = mutableStateOf(""),
            )
        }
    }


    fun retryDownloadGlbFile(applicationContext: Context, name: String) = viewModelScope.launch {
        if (name == "body") {
            downloadGlbFile(applicationContext, _modelFromPhotoConstructorUIState.value.modelPartsList.value?.body ?: "", "body", _modelFromPhotoConstructorUIState.value.body)
        }
        else {
            if (name == "hair") {
                downloadGlbFile(applicationContext, _modelFromPhotoConstructorUIState.value.modelPartsList.value?.hair ?: "", "hair", _modelFromPhotoConstructorUIState.value.hair)
            }
            else {
                downloadGlbFile(applicationContext, _modelFromPhotoConstructorUIState.value.modelPartsList.value?.eye ?: "", "eye", _modelFromPhotoConstructorUIState.value.eyes)
            }
        }
    }
    fun changeSkyBoxColor(color: Color) {
        updateUIState {
            copy (
                skyBoxColor = mutableStateOf(color)
            )
        }
    }

    private fun updateProgressCoolByName(fileName: String, progress: String?) {
        if (fileName == "body") {
            updateUIState {
                copy (
                    body = Resource.loading(progress)
                )
            }
        }
        if (fileName == "hair") {
            updateUIState {
                copy (
                    hair = Resource.loading(progress)
                )
            }
        }
        if (fileName == "eye") {
            updateUIState {
                copy (
                    eyes = Resource.loading(progress)
                )
            }
        }
    }

    private fun updateCoolByName(fileName: String, state: Resource<String>) {
        if (fileName == "body") {
            updateUIState {
                copy(
                    body = state
                )
            }
        }
        if (fileName == "hair") {
            updateUIState {
                copy(
                    hair = state
                )
            }
        }
        if (fileName == "eye") {
            updateUIState {
                copy(
                    eyes = state
                )
            }
        }
    }

    fun clearScene() {
        updateUIState {
            copy(
                clearScene = mutableStateOf(true)
            )
        }
    }

    fun updateCanGoState() {
        updateUIState {
            copy (
                canGo = mutableStateOf(true)
            )
        }
    }

    fun updateIsDialogShown() {
        updateUIState {
            copy (
                isDialogShown = mutableStateOf(!isDialogShown.value)
            )
        }
    }

    fun updateIsModelRotating(state: Boolean) {
        updateUIState {
            copy (
                isModelRotating = mutableStateOf(state)
            )
        }
    }

    fun addToBasket() = viewModelScope.launch{
        val hairLink = dataStoreManager.getHairLink().first().orEmpty()
        val eyeLink = dataStoreManager.getEyeLink().first().orEmpty()
        val bodyLink = dataStoreManager.getBodyLink().first().orEmpty()
        val basketFigures = db.getFiguresByType(FigureType.CUSTOM_BY_PHOTO)
        basketFigures?.forEach {
            if (hairLink == it.hairLink && eyeLink == it.eyeLink && bodyLink == it.bodyLink) {
                return@launch
            }
        }
        db.insertFigureToBasket(
            BasketItemEntity(
                id = 0,
                type = FigureType.CUSTOM_BY_PHOTO.ordinal,
                hairLink = hairLink,
                eyeLink = eyeLink,
                bodyLink = bodyLink,
                count = 1
            )
        )
        val figure = db.getFigureByLinks(eyeLink, hairLink, bodyLink) ?: return@launch
        if (figure.id != 0 ) {
            updateUIState {
                copy(
                    currentFigureId = mutableIntStateOf(figure.id),
                    count = mutableIntStateOf(1)
                )
            }
        }
    }


    fun addButton() = viewModelScope.launch {
        val hairLink = dataStoreManager.getHairLink().first().orEmpty()
        val eyeLink = dataStoreManager.getEyeLink().first().orEmpty()
        val bodyLink = dataStoreManager.getBodyLink().first().orEmpty()
        val figure = db.getFigureByLinks(eyeLink, hairLink, bodyLink) ?: return@launch
        if (figure.id == 0) return@launch
        val tempCount = modelFromPhotoConstructorUIState.value.count.value + 1
        updateUIState {
            copy (
                count = mutableIntStateOf(tempCount)
            )
        }
        db.updateFigureCountByBasketItemId(figure.id, tempCount)
    }
    fun subtractButton() = viewModelScope.launch {
        val hairLink = dataStoreManager.getHairLink().first().orEmpty()
        val eyeLink = dataStoreManager.getEyeLink().first().orEmpty()
        val bodyLink = dataStoreManager.getBodyLink().first().orEmpty()
        val figure = db.getFigureByLinks(eyeLink, hairLink, bodyLink) ?: return@launch
        if (figure.id == 0) return@launch
        val tempCount = modelFromPhotoConstructorUIState.value.count.value - 1
        updateUIState {
            copy (
                count = mutableIntStateOf(tempCount)
            )
        }
        if (tempCount == 0) {
            db.deleteByBasketId(figure.id)
        }
        else {
            db.updateFigureCountByBasketItemId(figure.id, tempCount)
        }
    }

    private fun getCount() = viewModelScope.launch {
        val hairLink = dataStoreManager.getHairLink().first().orEmpty()
        val eyeLink = dataStoreManager.getEyeLink().first().orEmpty()
        val bodyLink = dataStoreManager.getBodyLink().first().orEmpty()
        val figure = db.getFigureByLinks(eyeLink, hairLink, bodyLink) ?: return@launch
        if (figure.id == 0) return@launch
        updateUIState {
            copy (
                count = mutableIntStateOf(figure.count)
            )
        }
    }

    fun checkIfUpdateNeeded() = viewModelScope.launch {
        val newData = modelFromPhotoConstructorUIState.value.modelPartsList
        if (newData.value?.eye != dataStoreManager.getEyeLink().first()) {
            dataStoreManager.setEyeLink(newData.value?.eye ?: "")
        }
        if (newData.value?.hair != dataStoreManager.getHairLink().first()) {
            dataStoreManager.setHairLink(newData.value?.hair ?: "")
        }
        if (newData.value?.body != dataStoreManager.getBodyLink().first()) {
            dataStoreManager.setBodyLink(newData.value?.body ?: "")
        }

    }

}