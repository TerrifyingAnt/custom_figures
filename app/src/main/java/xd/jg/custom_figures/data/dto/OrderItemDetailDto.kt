package xd.jg.custom_figures.data.dto

data class OrderItemDetailDto (
    val type: Int = 0, // тип фигуры: с каталога, кастомная по фото, кастомная по описанию
    var count: Int = 0, // количество фигурки
    val price: Float = 0f, // цена фигурки
    val title: String = "", // название фигурки
)