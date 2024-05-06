package xd.jg.custom_figures.presentation.auth_screen

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
class AuthViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val iAuthRepository: IAuthRepository

): ViewModel() {

    private val _authUIState = mutableStateOf(AuthUIState())
    val authUIState: State<AuthUIState> = _authUIState

    /** состояние всего*/
    private fun updateUIState(update: AuthUIState.() -> AuthUIState) {
        _authUIState.value = _authUIState.value.update()
    }

    /** обновление логина*/
    fun updateLogin(newText: String) {
        updateUIState {
            copy(
                login = mutableStateOf(newText)
            )
        }
        checkLoginErrors()
    }

    /** обновление пароля*/
    fun updatePassword(newText: String) {
        updateUIState {
            copy(
                password = mutableStateOf(newText)
            )
        }
        checkPasswordErrors()
    }

    fun login() = viewModelScope.launch {
        val response = iAuthRepository.login(authUIState.value.login.value, authUIState.value.password.value)
        if (response.data != null) {
            tokenManager.setAccessToken(response.data.accessToken)
            tokenManager.setRefreshToken(response.data.refreshToken)
            updateUIState {
                copy (
                    successAuth = Resource.success("Вы авторизованы!")
                )
            }
        }
        else {
            updateUIState {
                copy (
                    successAuth = Resource.error(response.status.name, response.message)
                )
            }
        }
    }

    /** проверка пароля на валидность */
    private fun isValidPassword(password: String): Boolean {
        // Password should be at least 8 characters long
        if (password.length < 8) {
            updateUIState {
                copy(
                    passwordErrors = mutableStateOf("Пароль должен содержать минимум 8 символов")
                )
            }
            return false
        }
        // Password should contain at least one uppercase letter
        if (!password.any { it.isUpperCase() }) {
            updateUIState {
                copy (
                    passwordErrors = mutableStateOf("Пароль должен содержать хотя бы одну заглавную букву")
                )
            }
            return false
        }

        // Password should contain at least one digit
        if (!password.any { it.isDigit() }) {
            updateUIState {
                copy (
                    passwordErrors = mutableStateOf("Пароль должен содержать хотя бы одну цифру")
                )
            }
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(Regex(emailPattern))
    }

    private fun checkPasswordErrors() {
        val password = _authUIState.value.password.value
        updateUIState {
            copy(
                isPasswordValid = mutableStateOf(isValidPassword(password))
            )
        }
    }

    private fun checkLoginErrors() {
        val login = _authUIState.value.login.value
        updateUIState {
            copy(
                isLoginValid = mutableStateOf(isValidEmail(login))
            )
        }
    }


}