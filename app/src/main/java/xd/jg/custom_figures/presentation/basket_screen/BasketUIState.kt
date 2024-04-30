package xd.jg.custom_figures.presentation.basket_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.utils.Resource

data class BasketUIState (
    val basketFigures: MutableState<List<BasketItemEntity>?> = mutableStateOf(listOf()),
    val previewDefaultFigures: Resource<List<FigurePreviewDto>> = Resource.loading(mutableListOf())
    
)