package xd.jg.custom_figures.presentation.main_screen.models

data class PartModelData(
    val imageSource: String,
    val partTitle: String,
    val onClick: () -> Unit?
)
