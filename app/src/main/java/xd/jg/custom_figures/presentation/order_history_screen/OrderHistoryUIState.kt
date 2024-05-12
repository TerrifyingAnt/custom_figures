package xd.jg.custom_figures.presentation.order_history_screen

import xd.jg.custom_figures.data.dto.OrderDto
import xd.jg.custom_figures.utils.Resource

data class OrderHistoryUIState (
    val historyData: Resource<List<OrderDto>> = Resource.loading()
)