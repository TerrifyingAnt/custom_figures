package xd.jg.custom_figures.domain.local

import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType

interface IBasketRepository {

    suspend fun insertFigureToBasket(figure: BasketItemEntity)

    suspend fun updateFigureCount(figureId: Int, count: Int)

    suspend fun updateFigureCountByBasketItemId(basketItemId: Int, count: Int)

    suspend fun deleteFigureById(figureId: Int)

    suspend fun getFigureCount(figureId: Int): Int?

    suspend fun getFigures(): List<BasketItemEntity>?

    suspend fun getFiguresByType(type: FigureType): List<BasketItemEntity>?

    suspend fun getFigureByLinks(eyeLink: String, hairLink: String, bodyLink: String): BasketItemEntity?

    suspend fun deleteByBasketId(basketId: Int)

    suspend fun getFigureByTitle(figureTitle: String): BasketItemEntity?

    suspend fun getFigureByBasketId(basketId: Int): BasketItemEntity?

}