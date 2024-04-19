package xd.jg.custom_figures.domain.remote

import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.data.dto.TagTitleDto
import xd.jg.custom_figures.utils.Resource
import java.io.File

interface IFigureRepository {

    /** Для отправки фотографии на сервер --> перенести дальше в репозиторий для конструктора */
    suspend fun sendImage(imageFile: File): Resource<ModelPartListDto>

    /** Для скачивания модели с сервера --> перенести дальше в репозиторий для конструктора */
    suspend fun getModel(urlPath: String): Resource<ResponseBody>

    /** Для получения превьюшной информации о фигурке с сервера */
    suspend fun getFigures(filters: List<String>, figureTitle: String, page: Int): Resource<List<FigurePreviewDto>>

    /** Для получение тэгов с сервера */
    suspend fun getTags(): Resource<List<TagTitleDto>>

    suspend fun getFigureById(figureId: Int): Resource<FigureDto>
}