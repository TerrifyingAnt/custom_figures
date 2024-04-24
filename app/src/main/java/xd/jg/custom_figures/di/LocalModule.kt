package xd.jg.custom_figures.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xd.jg.custom_figures.data.local.AppDatabase
import xd.jg.custom_figures.data.local.DataStoreManager
import xd.jg.custom_figures.data.local.dao.BasketDao
import xd.jg.custom_figures.data.repository.local.BasketRepository
import xd.jg.custom_figures.domain.local.IBasketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "custom_figures_app_db"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun providesBasketDao(db: AppDatabase): BasketDao = db.basketDao() // The reason we can implement a Dao for the database

    @Singleton
    @Provides
    fun provideIBasketRepository(basketDao: BasketDao): IBasketRepository {
        return BasketRepository(basketDao)
    }
}
