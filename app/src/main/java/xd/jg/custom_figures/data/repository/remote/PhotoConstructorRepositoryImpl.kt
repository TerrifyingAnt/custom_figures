package xd.jg.custom_figures.data.repository.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IPhotoConstructorClient
import xd.jg.custom_figures.domain.remote.IPhotoConstructorRepository
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.Resource
import java.io.File
import javax.inject.Inject

class PhotoConstructorRepositoryImpl @Inject constructor(
    private val iPhotoConstructorClient: IPhotoConstructorClient
) : BaseDataSource(), IPhotoConstructorRepository {

    override suspend fun sendImage(imageFile: File): Resource<ModelPartListDto> {
        val requestFile = imageFile.asRequestBody("image/png".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("item", imageFile.name, requestFile)
        return safeApiCall {
            iPhotoConstructorClient.sendPhoto(
                body,
                "${Constants.BASE_URL}photo"
            )
        }
    }

    override suspend fun getModel(urlPath: String): Resource<ResponseBody> {
        return safeApiCall { iPhotoConstructorClient.downloadFile(urlPath) }
    }
}