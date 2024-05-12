package xd.jg.custom_figures.utils

import androidx.compose.ui.geometry.Offset
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.OrderItemDto
import xd.jg.custom_figures.data.dto.TagDto
import xd.jg.custom_figures.data.dto.TagTitleDto
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.domain.model.TagFilter
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt


fun Offset.getAngle(center: Offset): Float {
    val dx = x - center.x
    val dy = y - center.y
    return (atan2(dy.toDouble(), dx.toDouble()) * 180 / PI).toFloat() + 90
}

fun Offset.getDistance(center: Offset): Float {
    val dx = x - center.x
    val dy = y - center.y
    return sqrt(dx * dx + dy * dy)
}

fun TagTitleDto.toTagFilter(): TagFilter {
    return TagFilter(tagTitle = this.tagTitle)
}

fun TagDto.toTagFilter() : TagFilter {
    return TagFilter(tagTitle = this.title)
}

fun FigureDto.toBasketItemEntity(): BasketItemEntity {
    return BasketItemEntity(
        id = 0,
        type = FigureType.CUSTOM_DEFAULT.ordinal,
        figureId = this.id,
        description = this.description,
        title = this.title,
        count = 1,
        price = this.price)
}

fun BasketItemEntity.toOrderItemDto(): OrderItemDto {
    return OrderItemDto(
        this.type,
        this.figureId,
        this.description,
        this.references,
        this.movable,
        this.colored,
        this.hairLink,
        this.eyeLink,
        this.bodyLink,
        this.count,
        this.price,
        this.title
    )
}