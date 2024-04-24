package xd.jg.custom_figures.data.repository.local

import xd.jg.custom_figures.data.local.dao.BasketDao
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
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

    override suspend fun deleteFigureById(figureId: Int) {
        return basketDao.deleteFigureById(figureId)
    }

    override suspend fun getFigureCount(figureId: Int): Int? {
        return basketDao.getFigureCount(figureId)
    }
}