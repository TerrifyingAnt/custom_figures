package xd.jg.custom_figures.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xd.jg.custom_figures.data.remote.IFigureClient
import xd.jg.custom_figures.data.repository.remote.FigureRepositoryImpl
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.domain.use_case.SendImageUseCase
import xd.jg.custom_figures.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideFiguresClient(retrofit: Retrofit): IFigureClient =
        retrofit.create(IFigureClient::class.java)

    @Provides
    @Singleton
    fun provideIFiguresClient(iFigureClient: IFigureClient): IFigureRepository = FigureRepositoryImpl(iFigureClient)

    @Provides
    @Singleton
    fun provideSendImageUseCase(iFigureRepository: IFigureRepository): SendImageUseCase = SendImageUseCase(iFigureRepository)
}