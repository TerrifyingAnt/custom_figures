package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.models

import androidx.compose.ui.graphics.Color
import xd.jg.custom_figures.utils.Resource

data class PartModelData(
    val color: Color,
    val partTitle: String,
    val state: Resource<String>
)
