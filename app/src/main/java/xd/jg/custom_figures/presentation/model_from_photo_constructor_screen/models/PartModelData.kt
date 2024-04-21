package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.models

import xd.jg.custom_figures.utils.Resource

data class PartModelData(
    val imageSource: String,
    val partTitle: String,
    val state: Resource<String>
)
