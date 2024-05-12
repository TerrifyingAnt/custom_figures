package xd.jg.custom_figures.presentation.order_history_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xd.jg.custom_figures.data.dto.OrderDto
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.data.model.OrderStatus
import xd.jg.custom_figures.ui.theme.CustomOnPrimaryFixedVariant
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.ui.theme.robotoRegularFont
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun OrderItem(orderItem: OrderDto) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(shape = RoundedCornerShape(ROUNDED.dp))
        .background(
            CustomPrimaryContainer
        ), contentAlignment = Alignment.Center) {
        Column() {
            Row() {
                Text(
                    "Заказ №${orderItem.orderInfo.orderId} от ${
                        orderItem.orderInfo.date?.split(" ")?.first()?.replace("/", ".")
                    }", fontFamily = robotoRegularFont, color = CustomOnPrimaryFixedVariant, fontSize = 20.sp
                )
            }
            Row() {

                Text(
                    "Статус заказа: ${OrderStatus.fromEnum(orderItem.orderInfo.state ?: "ACCEPT")}", fontFamily = robotoRegularFont, color = CustomOnPrimaryFixedVariant, fontSize = 20.sp
                )
            }
            Row() {
                Text(
                    "Состав заказа:",
                    fontFamily = robotoRegularFont,
                    color = CustomOnPrimaryFixedVariant,
                    fontSize = 20.sp
                )
            }
            orderItem.orderDetails.forEach {
                Row() {
                    var title = ""
                    if (it.type == FigureType.CUSTOM_BY_TEXT.ordinal) title =
                        "Фигурка по описанию:"
                    Text(
                        "   - $title ${it.title} x${it.count}",
                        fontFamily = robotoRegularFont,
                        color = CustomOnPrimaryFixedVariant,
                        fontSize = 20.sp
                    )
                }
            }
            Divider()
            Row() {
                Text(
                    "Итого: ${orderItem.orderDetails.sumOf { (it.count).toDouble() * it.price }}",
                    fontFamily = robotoRegularFont,
                    color = CustomOnPrimaryFixedVariant,
                    fontSize = 20.sp
                )
            }
            Spacer(Modifier.size(10.dp))
        }

    }
}