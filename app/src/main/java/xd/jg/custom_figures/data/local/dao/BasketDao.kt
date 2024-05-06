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

    @Query("SELECT * FROM BasketItemEntity")
    suspend fun getFigures(): List<BasketItemEntity>?

    @Query("SELECT * FROM BasketItemEntity WHERE type=:type")
    suspend fun getFigureByType(type: Int): List<BasketItemEntity>?

    @Query("SELECT * FROM BasketItemEntity WHERE eyeLink=:eyeLink AND hairLink=:hairLink AND bodyLink=:bodyLink")
    suspend fun getFigureByLinks(eyeLink: String, hairLink: String, bodyLink: String): BasketItemEntity?

    @Query("UPDATE BasketItemEntity SET count=:count WHERE id=:id")
    suspend fun updateFigureCountByBasketItemId(id: Int, count: Int)

    @Query("DELETE FROM BasketItemEntity WHERE id=:id")
    suspend fun deleteByBasketId(id: Int)

    @Query("SELECT * FROM BasketItemEntity WHERE title=:title AND type=:type")
    suspend fun getFigureByTitle(title: String, type: Int): BasketItemEntity?

    @Query("SELECT * FROM BasketItemEntity WHERE id=:id")
    suspend fun getFigureByBasketId(id: Int): BasketItemEntity?

    @Query("DELETE FROM BasketItemEntity")
    suspend fun deleteAll()

}