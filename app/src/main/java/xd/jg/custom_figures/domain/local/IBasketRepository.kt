package xd.jg.custom_figures.domain.local

import xd.jg.custom_figures.data.local.entity.BasketItemEntity

interface IBasketRepository {

    suspend fun insertFigureToBasket(figure: BasketItemEntity)

    suspend fun updateFigureCount(figureId: Int, count: Int)

    suspend fun deleteFigureById(figureId: Int)

    suspend fun getFigureCount(figureId: Int): Int?
}