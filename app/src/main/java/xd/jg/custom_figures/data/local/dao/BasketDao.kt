package xd.jg.custom_figures.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import xd.jg.custom_figures.data.local.entity.BasketItemEntity

@Dao
interface BasketDao {

    @Insert
    suspend fun insertFigureToBasket(item: BasketItemEntity)

    @Query("UPDATE BasketItemEntity SET count=:count WHERE figureId=:figureId")
    suspend fun updateFigureCount(figureId: Int, count: Int)

    @Query("DELETE FROM BasketItemEntity WHERE figureId=:figureId")
    suspend fun deleteFigureById(figureId: Int)

    @Query("SELECT count FROM BasketItemEntity WHERE figureId=:figureId")
    suspend fun getFigureCount(figureId: Int): Int?
}