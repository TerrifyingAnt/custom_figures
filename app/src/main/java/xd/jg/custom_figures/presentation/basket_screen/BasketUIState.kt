package xd.jg.custom_figures.presentation.basket_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.data.local.entity.BasketItemEntity

data class BasketUIState (
    val figures: MutableState<List<BasketItemEntity>> = mutableStateOf(mutableListOf()),
)