package xd.jg.custom_figures.presentation.order_history_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.domain.remote.IOrderRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val iOrderRepository: IOrderRepository
): ViewModel() {

    private val _orderHistoryUIState = mutableStateOf(OrderHistoryUIState())
    val orderHistoryUIState: State<OrderHistoryUIState> = _orderHistoryUIState

    private fun updateUIState(update: OrderHistoryUIState.() -> OrderHistoryUIState) {
        _orderHistoryUIState.value = _orderHistoryUIState.value.update()
    }
    fun getOrders() = viewModelScope.launch {
        val response = iOrderRepository.getOrders()
        if (response.data != null) {
            updateUIState {
                copy (
                    historyData = Resource.success(response.data)
                )
            }
        }
    }
}