package xd.jg.custom_figures.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import xd.jg.custom_figures.data.dto.TokensDto
import xd.jg.custom_figures.data.dto.UserRegisterInfoDto
import xd.jg.custom_figures.utils.Constants.CLIENT_ID
import xd.jg.custom_figures.utils.Constants.CLIENT_SECRET
import xd.jg.custom_figures.utils.Constants.GRANT_TYPE

interface IAuthClient {
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(@Field("username") username: String,
                      @Field("password") password: String,
                      @Field("client_id") clientId: String = CLIENT_ID,
                      @Field("grant_type") grantType: String = GRANT_TYPE,
                      @Field("client_secret") clientSecret: String = CLIENT_SECRET): Response<TokensDto>

    @POST("/register")
    suspend fun register(@Body user: UserRegisterInfoDto): Response<Any>

}