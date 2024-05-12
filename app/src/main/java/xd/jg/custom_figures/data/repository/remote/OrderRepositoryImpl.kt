package xd.jg.custom_figures.data.repository.remote

import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.OrderDto
import xd.jg.custom_figures.data.dto.OrderItemDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IOrderClient
import xd.jg.custom_figures.domain.remote.IOrderRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val iOrderClient: IOrderClient
) : BaseDataSource(), IOrderRepository {
    override suspend fun createOrder(order: List<OrderItemDto>): Resource<ResponseBody> {
        return safeApiCall { iOrderClient.createOrder(order) }
    }

    override suspend fun getOrders(): Resource<List<OrderDto>> {
        return safeApiCall { iOrderClient.getOrders() }
    }

}