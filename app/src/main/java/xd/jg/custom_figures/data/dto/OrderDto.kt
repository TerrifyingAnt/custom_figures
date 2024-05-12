package xd.jg.custom_figures.data.dto

import xd.jg.custom_figures.data.local.entity.Order

data class OrderDto(
    val orderInfo: Order,
    val orderDetails: List<OrderItemDetailDto>
)
