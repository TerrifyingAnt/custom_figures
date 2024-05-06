package xd.jg.custom_figures.data.remote

import retrofit2.Response
import retrofit2.http.GET
import xd.jg.custom_figures.data.dto.UserInfoDto

interface IProfileClient {
    @GET("/me")
    suspend fun getMe(): Response<UserInfoDto>
}