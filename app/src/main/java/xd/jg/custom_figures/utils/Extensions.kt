package xd.jg.custom_figures.utils

import androidx.compose.ui.geometry.Offset
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
