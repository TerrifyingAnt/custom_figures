package xd.jg.custom_figures.domain.remote

import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.utils.Resource
import java.io.File

interface IPhotoConstructorRepository {
    /** Для отправки фотографии на сервер --> перенести дальше в репозиторий для конструктора */
    suspend fun sendImage(imageFile: File): Resource<ModelPartListDto>

    /** Для скачивания модели с сервера --> перенести дальше в репозиторий для конструктора */
    suspend fun getModel(urlPath: String): Resource<ResponseBody>
}