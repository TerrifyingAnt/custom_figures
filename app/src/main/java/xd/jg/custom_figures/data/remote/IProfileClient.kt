package xd.jg.custom_figures.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url
import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.utils.Constants.BASE_MINIO_LINK

interface IProfileClient {
    @GET("/me")
    suspend fun getMe(): Response<UserInfoDto>

    @Multipart
    @POST("/update_avatar")
    suspend fun updateAvatar(@Part("image") image: RequestBody): Response<String>

    @GET("/work_update_avatar")
    suspend fun workUpdateAvatar(): Response<Map<String, String>>

    @Multipart
    @POST
    suspend fun uploadAvatarToMinio(@Part partData: List<MultipartBody.Part>, @Url link: String = BASE_MINIO_LINK): Response<ResponseBody>

    @POST("/update_user")
    suspend fun updateUserInfo(@Query("user_name") userName: String?, @Query("user_phone") userPhone: String?): Response<ResponseBody>
}