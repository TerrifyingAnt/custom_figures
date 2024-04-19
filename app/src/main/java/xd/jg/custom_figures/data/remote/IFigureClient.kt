package xd.jg.custom_figures.data.remote

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.data.dto.TagTitleDto

interface IFigureClient {
    @Multipart
    @POST("/photo")
    suspend fun sendPhoto(@Part image: MultipartBody.Part): Response<ModelPartListDto>

    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

    @GET("/figures")
    suspend fun getFigures(@Query("filters") filters: List<String>, @Query("figure_title") figureTitle: String, @Query("page") page: Int): Response<List<FigurePreviewDto>>

    @GET("/tags")
    suspend fun getTags(): Response<List<TagTitleDto>>

}