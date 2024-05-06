package xd.jg.custom_figures.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import xd.jg.custom_figures.data.local.entity.BasketItemEntity

interface IOrderClient {

    @POST("/orders/create")
    suspend fun createOrder(@Body body: List<BasketItemEntity>): Response<Any>

}