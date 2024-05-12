package xd.jg.custom_figures.presentation.basket_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.local.TokenManager
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.domain.local.IBasketRepository
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.domain.remote.IOrderRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.toOrderItemDto
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val db: IBasketRepository,
    private val iFigureRepository: IFigureRepository,
    private val iOrderRepository: IOrderRepository,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _basketUIState = mutableStateOf(BasketUIState())
    val basketUIState: State<BasketUIState> = _basketUIState

    /** состояние всего*/
    private fun updateUIState(update: BasketUIState.() -> BasketUIState) {
        _basketUIState.value = _basketUIState.value.update()
    }

    /** получение информации оо фигурках из корзины*/
    fun getAllBasketItems() = viewModelScope.launch {
        val figures = db.getFigures()
        val catalogFigures = figures?.filter {it.type == FigureType.CUSTOM_DEFAULT.ordinal}?.map {it.figureId} ?: listOf()
        getFiguresFromServer(catalogFigures)
        updateUIState {
            copy (
                basketFigures = mutableStateOf(figures)
            )
        }
    }

    /** получение превью с сервера*/
    private fun getFiguresFromServer(ids: List<Int>) = viewModelScope.launch {
        val listCatalogFigures = mutableListOf<FigurePreviewDto>()
        val response = iFigureRepository.getFigurePreviewByIds(ids)
        if (response.data != null) {
            listCatalogFigures.addAll(response.data)
            updateUIState {
                copy (
                    previewDefaultFigures = Resource.success(listCatalogFigures)
                )
            }
        }
    }

    /** увеличение количества фигурок в корзине*/
    fun addFigureCount(figureId: Int) = viewModelScope.launch {
        val figure = basketUIState.value.basketFigures.value?.find {it.id == figureId}
        figure?.count = figure?.count?.plus(1) ?: 1
        val newListFigures = mutableListOf<BasketItemEntity>()
        basketUIState.value.basketFigures.value?.forEach {
            if (it.id == figureId) {
                it.count = figure?.count ?: 1
            }
            newListFigures.add(it)
        }
        db.updateFigureCountByBasketItemId(figureId, figure?.count ?: 1)
        updateUIState {
            copy (
                basketFigures = mutableStateOf(newListFigures)
            )
        }
    }

    /** уменьшение количества фигурок в корзине*/
    fun subtractFigureCount(figureId: Int) = viewModelScope.launch {
        val tempFigure = basketUIState.value.basketFigures.value?.find {it.id == figureId}
        val tempCount = tempFigure?.count ?: 1
        if (tempCount - 1 <= 0) {
            db.deleteByBasketId(figureId)
            updateUIState {
                copy (
                    basketFigures = mutableStateOf(basketFigures.value?.filter { it.id != figureId })
                )
            }
        }
        else {
            db.updateFigureCountByBasketItemId(figureId, tempCount - 1)
            val figure = basketUIState.value.basketFigures.value?.find {it.id == figureId}
            figure?.count = figure?.count?.minus(1) ?: 1
            val newListFigures = mutableListOf<BasketItemEntity>()
            basketUIState.value.basketFigures.value?.forEach {
                if (it.id == figureId) {
                    it.count = figure?.count ?: 1

                }
                newListFigures.add(it)
            }
            updateUIState {
                copy (
                    basketFigures = mutableStateOf(newListFigures)
                )
            }
        }
    }

    /** создать заказ */
    fun createOrder() = viewModelScope.launch {
        val listOfItems = _basketUIState.value.basketFigures.value ?: return@launch
        val orderItemsList = listOfItems.map {it.toOrderItemDto()}
        iOrderRepository.createOrder(orderItemsList)
        db.deleteAll()
        updateUIState {
            copy(
                basketFigures = mutableStateOf(mutableListOf()),
                previewDefaultFigures = Resource.loading()
            )
        }
    }

    /** проверка наличия токенов */
    fun checkIfTokensExist() = viewModelScope.launch {
        val tokenExist = tokenManager.getAccessToken().first()
        if (tokenExist != "" && tokenExist != null) {
            updateUIState {
                copy (
                    authorized = mutableStateOf(true)
                )
            }
        }
    }

}