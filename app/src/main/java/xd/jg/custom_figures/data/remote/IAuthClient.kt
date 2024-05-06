package xd.jg.custom_figures.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xd.jg.custom_figures.data.dto.TokensDto

interface IAuthClient {
    @GET("/login")
    suspend fun login(@Query("username") login: String, @Query("password") password: String): Response<TokensDto>
}