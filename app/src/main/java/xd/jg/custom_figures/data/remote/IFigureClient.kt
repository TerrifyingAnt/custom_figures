package xd.jg.custom_figures.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.dto.TagTitleDto

interface IFigureClient {

    @GET("/figures")
    suspend fun getFigures(@Query("filters") filters: List<String>, @Query("figure_title") figureTitle: String, @Query("page") page: Int): Response<List<FigurePreviewDto>>

    @GET("/tags")
    suspend fun getTags(): Response<List<TagTitleDto>>

    @GET("/figures/{id}")
    suspend fun getFigureById(@Path(value = "id", encoded = true) figureId: Int): Response<FigureDto>

    @GET("/figures_preview")
    suspend fun getFigurePreviewByIds(@Query("ids") ids: List<Int>, @Query("page") page: Int = 0): Response<List<FigurePreviewDto>>

}