package xd.jg.custom_figures.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import xd.jg.custom_figures.data.local.dao.BasketDao
import xd.jg.custom_figures.data.local.entity.BasketItemEntity

@Database(entities = [
    BasketItemEntity::class
],
    version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun basketDao(): BasketDao
}