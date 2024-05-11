package xd.jg.custom_figures.presentation.edit_profile_screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import xd.jg.custom_figures.domain.remote.IProfileRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val iProfileRepository: IProfileRepository
): ViewModel() {
    private val _editProfileUIState = mutableStateOf(EditProfileUIState())
    val editProfileUIState: State<EditProfileUIState> = _editProfileUIState

    /** состояние всего*/
    private fun updateUIState(update: EditProfileUIState.() -> EditProfileUIState) {
        _editProfileUIState.value = _editProfileUIState.value.update()
    }

    /** обновление имени */
    fun updateUserName(newValue: String) {
        updateUIState {
            copy (
                newUserName = mutableStateOf(newValue)
            )
        }
    }

    fun updateToDefaultPhoneAndName() {
        updateUIState {
            copy (
                nameUpdated = Resource.loading(),
                phoneUpdated = Resource.loading()
            )
        }
    }

    /** обновление телефона */
    fun updateUserPhone(newValue: String) {
        updateUIState {
            copy (
                newUserPhone = mutableStateOf(newValue)
            )
        }
    }

    fun updateOldUserInfo(name: String, email: String, phone: String) {
        updateUIState {
            copy (
                oldUserName = mutableStateOf(name),
                oldUserEmail = mutableStateOf(email),
                oldUserPhone = mutableStateOf(phone)
            )
        }
    }

    fun saveChanges() = viewModelScope.launch {
        if (editProfileUIState.value.newUserName.value.replace(" ", "") != "") {
            val response = iProfileRepository.updateUserInfo(editProfileUIState.value.newUserName.value)
            if (response.message?.contains("204") == true) {
                updateUIState {
                    copy (
                        nameUpdated = Resource.success("Имя обновлено")
                    )
                }
            } else {
                updateUIState {
                    copy (
                        nameUpdated = Resource.error("Что-то пошло не так")
                    )
                }
            }
        }
        if (editProfileUIState.value.newUserPhone.value != "") {
            val response = iProfileRepository.updateUserInfo("", editProfileUIState.value.newUserPhone.value)
            if (response.message?.contains("204") == true) {
                updateUIState {
                    copy (
                        phoneUpdated = Resource.success("Телефон обновлен")
                    )
                }
            } else {
                updateUIState {
                    copy (
                        phoneUpdated = Resource.error("Что-то пошло не так")
                    )
                }
            }
        }
    }

    fun updateAvatarSourcePath(context: Context, avatarUri: Uri) = viewModelScope.launch {
        val inputStream = context.contentResolver.openInputStream(avatarUri)
        val requestBody = inputStream?.readBytes()?.toRequestBody("image/*".toMediaType()) ?: return@launch

        val response = iProfileRepository.workUpdateAvatar()
        if (response.data != null) {
            uploadImageToMinio(response.data, requestBody)
        }
    }

    fun updateAvatarResult(state: Boolean) {
        if (state) {
            updateUIState {
                copy (
                    avatarUpdated = Resource.success("")
                )
            }
        }
        else {
            updateUIState {
                copy (
                    avatarUpdated = Resource.loading("")
                )
            }
        }
    }

    private fun uploadImageToMinio(formData: Map<String, String>, requestBody: RequestBody) = viewModelScope.launch {
        val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)

        // Add form data
        for ((key, value) in formData.entries) {
            multipartBuilder.addFormDataPart(key, value)
        }

        Log.d("USER_EMAIL", editProfileUIState.value.oldUserEmail.value)
        multipartBuilder.addFormDataPart("key", "${editProfileUIState.value.oldUserEmail.value}/avatar")
        multipartBuilder.addFormDataPart("Content-Type", "image/*")

        multipartBuilder.addFormDataPart(
            "file", "${editProfileUIState.value.oldUserEmail.value}/avatar",
            requestBody
        )

        val response = iProfileRepository.uploadAvatarToMinio(multipartBuilder.build().parts)

        if (response.message?.contains("204") == true) {
            updateUIState {
                copy (
                    avatarUpdated = Resource.success("")
                )
            }
        } else {
            Log.d("XD", response.message ?: "Upload failed")
            updateUIState {
                copy (
                    avatarUpdated = Resource.error("")
                )
            }
        }
    }


}