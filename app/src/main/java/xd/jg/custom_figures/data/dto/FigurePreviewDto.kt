package xd.jg.custom_figures.data.dto

data class FigurePreviewDto(
    val id: Int,
    val title: String,
    val price: Float,
    val imageLinks: List<String>,
    val tags: MutableList<TagDto>
)