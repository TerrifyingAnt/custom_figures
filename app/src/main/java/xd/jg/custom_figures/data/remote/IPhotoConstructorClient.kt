package xd.jg.custom_figures.data.remote

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url
import xd.jg.custom_figures.data.dto.ModelPartListDto

interface IPhotoConstructorClient {
    @Multipart
    @POST
    suspend fun sendPhoto(@Part image: MultipartBody.Part, @Url url: String): Response<ModelPartListDto>

    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>
}