package xd.jg.custom_figures.domain.remote

import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.OrderDto
import xd.jg.custom_figures.data.dto.OrderItemDto
import xd.jg.custom_figures.utils.Resource

interface IOrderRepository {
    suspend fun createOrder(order: List<OrderItemDto>): Resource<ResponseBody>

    suspend fun getOrders(): Resource<List<OrderDto>>
}