package xd.jg.custom_figures.presentation.figure_detail_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.utils.Resource

/**
 * @param figureDetails хранит информацию о фигурке
 * @param figureModel хранит информацию о пути до файла 3д модели фигурки
 * @param clearScene когда == true, то происходит очистка сцены
 * */
data class FigureDetailUIState (
    val figureDetails: Resource<FigureDto> = Resource.loading(),
    var figureModel: Resource<String> = Resource.loading(),
    val clearScene: MutableState<Boolean> = mutableStateOf(false),
    val count: MutableState<Int> = mutableIntStateOf(0)
)