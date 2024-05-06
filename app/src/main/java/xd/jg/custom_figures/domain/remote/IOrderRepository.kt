package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.local.entity.BasketItemEntity

interface IOrderRepository {
    suspend fun createOrder(order: List<BasketItemEntity>)
}