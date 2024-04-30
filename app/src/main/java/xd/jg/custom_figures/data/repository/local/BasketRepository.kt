package xd.jg.custom_figures.data.repository.local

import xd.jg.custom_figures.data.local.dao.BasketDao
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.domain.local.IBasketRepository
import javax.inject.Inject

class BasketRepository @Inject constructor(
    private val basketDao: BasketDao
): IBasketRepository {
    override suspend fun insertFigureToBasket(figure: BasketItemEntity) {
       return basketDao.insertFigureToBasket(figure)
    }

    override suspend fun updateFigureCount(figureId: Int, count: Int) {
        return basketDao.updateFigureCount(figureId, count)
    }

    override suspend fun updateFigureCountByBasketItemId(basketItemId: Int, count: Int) {
        return basketDao.updateFigureCountByBasketItemId(basketItemId, count)
    }

    override suspend fun deleteFigureById(figureId: Int) {
        return basketDao.deleteFigureById(figureId)
    }

    override suspend fun getFigureCount(figureId: Int): Int? {
        return basketDao.getFigureCount(figureId)
    }

    override suspend fun getFigures(): List<BasketItemEntity>? {
        return basketDao.getFigures()
    }

    override suspend fun getFiguresByType(type: FigureType): List<BasketItemEntity>? {
        return basketDao.getFigureByType(type.ordinal)
    }

    override suspend fun getFigureByLinks(
        eyeLink: String,
        hairLink: String,
        bodyLink: String
    ): BasketItemEntity? {
        return basketDao.getFigureByLinks(eyeLink, hairLink, bodyLink)
    }

    override suspend fun deleteByBasketId(basketId: Int) {
        return basketDao.deleteByBasketId(basketId)
    }

    override suspend fun getFigureByTitle(figureTitle: String): BasketItemEntity? {
        return basketDao.getFigureByTitle(figureTitle, FigureType.CUSTOM_BY_TEXT.ordinal)
    }
}