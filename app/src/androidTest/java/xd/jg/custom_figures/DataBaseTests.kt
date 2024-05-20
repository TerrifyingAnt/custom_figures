package xd.jg.custom_figures

import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import xd.jg.custom_figures.data.local.dao.BasketDao
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.data.repository.local.BasketRepository

@RunWith(MockitoJUnitRunner::class)
class DataBaseTests {

    @Mock
    private lateinit var basketDao: BasketDao

    private lateinit var basketRepository: BasketRepository

    @Before
    fun setup() {
        basketRepository = BasketRepository(basketDao)
    }

    @Test
    fun testInsertFigureToBasket() = runBlocking {
        val figure = mockk<BasketItemEntity>()
        basketRepository.insertFigureToBasket(figure)
        verify(basketDao).insertFigureToBasket(figure)
    }

    @Test
    fun testUpdateFigure() = runBlocking {
        val figureId = 1
        val count = 5
        basketRepository.updateFigureCount(figureId, count)
        verify(basketDao).updateFigureCount(figureId, count)
    }

    @Test
    fun testUpdateBadFigure() = runBlocking {
        val basketItemId = 1
        val count = 5
        basketRepository.updateFigureCountByBasketItemId(basketItemId, count)
        verify(basketDao).updateFigureCountByBasketItemId(basketItemId, count)
    }

    @Test
    fun testDeleteFigure() = runBlocking {
        val figureId = 1
        basketRepository.deleteFigureById(figureId)
        verify(basketDao).deleteFigureById(figureId)
    }

    @Test
    fun testGetFigureCount() = runBlocking {
        val figureId = 1
        val expectedCount = 1
        `when`(basketDao.getFigureCount(figureId)).thenReturn(expectedCount)
        val result = basketRepository.getFigureCount(figureId)
        Assert.assertEquals(expectedCount, result)
    }

    @Test
    fun testGetA() = runBlocking {
        val expectedFigures = listOf(mockk<BasketItemEntity>(), mockk())
        `when`(basketDao.getFigures()).thenReturn(expectedFigures)
        val result = basketRepository.getFigures()
        Assert.assertEquals(result, expectedFigures)
    }

    @Test
    fun testGetFigureByType() = runBlocking {
        val type = FigureType.CUSTOM_BY_TEXT
        basketRepository.getFiguresByType(type)
        verify(basketDao).getFigureByType(type.ordinal)
    }

    @Test
    fun getFigureByLink() = runBlocking {
        val eyeLink = "eye_link"
        val hairLink = "hair_link"
        val bodyLink = "body_link"
        basketRepository.getFigureByLinks(eyeLink, hairLink, bodyLink)
        verify(basketDao).getFigureByLinks(eyeLink, hairLink, bodyLink)
    }
}