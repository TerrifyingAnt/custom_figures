package xd.jg.custom_figures.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import xd.jg.custom_figures.data.dto.OrderDto
import xd.jg.custom_figures.data.dto.OrderItemDto

interface IOrderClient {

    @POST("/orders/create_order")
    suspend fun createOrder(@Body body: List<OrderItemDto>): Response<ResponseBody>

    @GET("/orders/get_orders")
    suspend fun getOrders(): Response<List<OrderDto>>

}