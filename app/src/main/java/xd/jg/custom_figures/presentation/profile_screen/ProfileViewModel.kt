package xd.jg.custom_figures.presentation.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.local.TokenManager
import xd.jg.custom_figures.domain.remote.IAuthRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val iAuthRepository: IAuthRepository,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _profileUIState = mutableStateOf(ProfileUIState())
    val profileUIState: State<ProfileUIState> = _profileUIState

    /** состояние всего*/
    private fun updateUIState(update: ProfileUIState.() -> ProfileUIState) {
        _profileUIState.value = _profileUIState.value.update()
    }

    fun getMe() = viewModelScope.launch {
        val response = iAuthRepository.getMe()
        if (response.data != null) {
            updateUIState {
                copy(
                    userInfo = Resource.success(response.data)
                )
            }
        }
    }

    fun exitProfile() = viewModelScope.launch {
        tokenManager.setAccessToken("")
        tokenManager.setRefreshToken("")
        updateUIState {
            copy (
                userInfo = Resource.error("Вы не авторизованы")
            )
        }
    }
}