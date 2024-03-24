package xd.jg.custom_figures.data.remote

import android.graphics.Bitmap
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import xd.jg.custom_figures.data.dto.ModelPartListDto

interface IFigureClient {
    @Multipart
    @POST("/photo")
    suspend fun sendPhoto(@Part image: MultipartBody.Part): Response<ModelPartListDto>

}