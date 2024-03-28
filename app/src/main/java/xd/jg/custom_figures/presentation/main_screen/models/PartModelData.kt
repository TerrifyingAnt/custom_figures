package xd.jg.custom_figures.presentation.main_screen.models

import androidx.compose.runtime.State
import xd.jg.custom_figures.utils.Resource

data class PartModelData(
    val imageSource: String,
    val partTitle: String,
    val state: State<Resource<String>>
)
