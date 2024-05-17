package xd.jg.custom_figures.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xd.jg.custom_figures.data.remote.AuthInterceptor
import xd.jg.custom_figures.data.remote.IAuthClient
import xd.jg.custom_figures.data.remote.IChatClient
import xd.jg.custom_figures.data.remote.IFigureClient
import xd.jg.custom_figures.data.remote.IOrderClient
import xd.jg.custom_figures.data.remote.IPhotoConstructorClient
import xd.jg.custom_figures.data.remote.IProfileClient
import xd.jg.custom_figures.data.repository.remote.AuthRepositoryImpl
import xd.jg.custom_figures.data.repository.remote.ChatRepositoryImpl
import xd.jg.custom_figures.data.repository.remote.FigureRepositoryImpl
import xd.jg.custom_figures.data.repository.remote.OrderRepositoryImpl
import xd.jg.custom_figures.data.repository.remote.PhotoConstructorRepositoryImpl
import xd.jg.custom_figures.data.repository.remote.ProfileRepositoryImpl
import xd.jg.custom_figures.domain.remote.IAuthRepository
import xd.jg.custom_figures.domain.remote.IChatRepository
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.domain.remote.IOrderRepository
import xd.jg.custom_figures.domain.remote.IPhotoConstructorRepository
import xd.jg.custom_figures.domain.remote.IProfileRepository

import xd.jg.custom_figures.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_MAIN_URL)
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
    fun providePhotoConstructorClient(retrofit: Retrofit): IPhotoConstructorClient =
        retrofit.create(IPhotoConstructorClient::class.java)

    @Provides
    @Singleton
    fun provideIPhotoConstructorClient(iPhotoConstructorClient: IPhotoConstructorClient): IPhotoConstructorRepository = PhotoConstructorRepositoryImpl(iPhotoConstructorClient)

    @Provides
    @Singleton
    fun provideOrderClient(retrofit: Retrofit): IOrderClient =
        retrofit.create(IOrderClient::class.java)

    @Provides
    @Singleton
    fun provideIOrderClient(iOrderClient: IOrderClient): IOrderRepository = OrderRepositoryImpl(iOrderClient)


    @Provides
    @Singleton
    fun provideProfileClient(retrofit: Retrofit): IProfileClient =
        retrofit.create(IProfileClient::class.java)

    @Provides
    @Singleton
    fun provideIProfileClient(iProfileClient: IProfileClient): IProfileRepository = ProfileRepositoryImpl(iProfileClient)

    @Provides
    @Singleton
    fun provideAuthClient(retrofit: Retrofit): IAuthClient =
        retrofit.create(IAuthClient::class.java)

    @Provides
    @Singleton
    fun provideIAuthClient(iAuthClient: IAuthClient): IAuthRepository = AuthRepositoryImpl(iAuthClient)


    @Provides
    @Singleton
    fun provideChatClient(retrofit: Retrofit): IChatClient =
        retrofit.create(IChatClient::class.java)

    @Provides
    @Singleton
    fun provideIChatClient(iChatClient: IChatClient): IChatRepository = ChatRepositoryImpl(iChatClient)

}