package xd.jg.custom_figures.data.repository.remote

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IProfileClient
import xd.jg.custom_figures.domain.remote.IProfileRepository
import xd.jg.custom_figures.utils.Resource
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val iProfileClient: IProfileClient
) : BaseDataSource(), IProfileRepository {
    override suspend fun getMe(): Resource<UserInfoDto> {
        return safeApiCall { iProfileClient.getMe() }
    }

    override suspend fun updateAvatar(imageFile: File): Resource<String> {
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        Log.d("WHY OR WHY", body.body.contentLength().toString() + " " + body.headers.toString())
        return safeApiCall {
            iProfileClient.updateAvatar(requestFile)
        }
    }

    override suspend fun workUpdateAvatar(): Resource<Map<String, String>> {
        return safeApiCall { iProfileClient.workUpdateAvatar() }
    }

    override suspend fun uploadAvatarToMinio(partData: List<MultipartBody.Part>): Resource<ResponseBody> {
        return safeApiCall { iProfileClient.uploadAvatarToMinio(partData) }
    }

    override suspend fun updateUserInfo(userName: String?, userPhone: String?): Resource<ResponseBody> {
        return safeApiCall { iProfileClient.updateUserInfo(userName, userPhone) }
    }


}