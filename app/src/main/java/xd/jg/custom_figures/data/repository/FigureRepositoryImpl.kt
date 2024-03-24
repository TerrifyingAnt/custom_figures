package xd.jg.custom_figures.data.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IFigureClient
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import java.io.File
import javax.inject.Inject

class FigureRepositoryImpl @Inject constructor(
    private val iFigureClient: IFigureClient
) : BaseDataSource(), IFigureRepository {
    override suspend fun sendImage(imageFile: File): Resource<ModelPartListDto> {
        val requestFile = imageFile.asRequestBody("image/png".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("item", imageFile.name, requestFile)
        return safeApiCall { iFigureClient.sendPhoto(body) }
    }
}