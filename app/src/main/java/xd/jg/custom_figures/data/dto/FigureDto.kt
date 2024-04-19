package xd.jg.custom_figures.data.dto

data class FigureDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Float,
    val rating: Float,
    val isMovable: Boolean,
    val timeOfMaking: String,
    val imageLinks: List<String>,
    val modelLink: String,
    val tags: MutableList<TagDto>
)