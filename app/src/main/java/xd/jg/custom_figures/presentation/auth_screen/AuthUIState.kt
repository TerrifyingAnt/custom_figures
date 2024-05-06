package xd.jg.custom_figures.presentation.auth_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.utils.Resource

data class AuthUIState (
    val login: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    val loginErrors: MutableState<String> = mutableStateOf(""),
    val passwordErrors: MutableState<String> = mutableStateOf(""),
    val isPasswordValid: MutableState<Boolean> = mutableStateOf(false),
    val isLoginValid: MutableState<Boolean> = mutableStateOf(false),
    val successAuth: Resource<String> = Resource.loading(),

)