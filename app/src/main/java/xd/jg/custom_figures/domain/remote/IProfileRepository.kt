package xd.jg.custom_figures.domain.remote

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.utils.Resource
import java.io.File

interface IProfileRepository {
    suspend fun getMe(): Resource<UserInfoDto>

    suspend fun updateAvatar(imageFile: File): Resource<String>

    suspend fun workUpdateAvatar(): Resource<Map<String, String>>

    suspend fun uploadAvatarToMinio(partData: List<MultipartBody.Part>): Resource<ResponseBody>

    suspend fun updateUserInfo(userName: String? = "", userPhone: String? = ""): Resource<ResponseBody>
}