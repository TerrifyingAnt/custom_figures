package xd.jg.custom_figures.presentation.custom_figure_detail_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.utils.Resource

data class CustomFigureDetailUIState(
    val currentFigure: MutableState<BasketItemEntity?> = mutableStateOf(null),
    val figureModel: Resource<Boolean> = Resource.loading(false),
    val hair: Resource<String> = Resource.loading(),
    val body: Resource<String> = Resource.loading(),
    val eyes: Resource<String> = Resource.loading()
)