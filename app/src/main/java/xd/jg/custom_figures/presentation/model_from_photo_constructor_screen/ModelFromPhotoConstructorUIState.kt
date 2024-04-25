package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.utils.Resource

data class ModelFromPhotoConstructorUIState(
    val photoWasMade: MutableState<Boolean> = mutableStateOf(false),
    val photoUri: MutableState<String> = mutableStateOf(""),
    val modelPartsList: MutableState<ModelPartListDto?> = mutableStateOf(null), // тут ссылки на сервер с модельками
    var eyes: Resource<String> = Resource.loading(), // тут абсолютные пути в андроиде до файлов с модельками
    var hair: Resource<String> = Resource.loading(),
    var body: Resource<String> = Resource.loading(),
    val figure: Resource<Boolean> = Resource.loading(false),
    val clearScene: MutableState<Boolean> = mutableStateOf(false),
    val canGo: MutableState<Boolean> = mutableStateOf(false),
    val skyBoxColor: MutableState<Color> = mutableStateOf(Color.White),
    val isDialogShown: MutableState<Boolean> = mutableStateOf(false),
    val isModelRotating: MutableState<Boolean> = mutableStateOf(true),
    val deleteModel: MutableState<Boolean> = mutableStateOf(false),
    val count: MutableState<Int> = mutableIntStateOf(0),
    val currentFigureId: MutableState<Int> = mutableIntStateOf(0)
)
