package xd.jg.custom_figures.data.repository.remote

import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IOrderClient
import xd.jg.custom_figures.domain.remote.IOrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val iOrderClient: IOrderClient
) : BaseDataSource(), IOrderRepository {
    override suspend fun createOrder(order: List<BasketItemEntity>) {
        safeApiCall { iOrderClient.createOrder(order) }
    }

}