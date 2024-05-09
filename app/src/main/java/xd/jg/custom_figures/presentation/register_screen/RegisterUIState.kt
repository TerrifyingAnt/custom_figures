package xd.jg.custom_figures.presentation.register_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.utils.Resource

data class RegisterUIState (
    val login: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    val repeatPassword: MutableState<String> = mutableStateOf(""),
    val fullName: MutableState<String> = mutableStateOf(""),

    val loginErrors: MutableState<String> = mutableStateOf(""),
    val passwordErrors: MutableState<String> = mutableStateOf(""),
    val isPasswordValid: MutableState<Boolean> = mutableStateOf(false),
    val isLoginValid: MutableState<Boolean> = mutableStateOf(false),

    val successAuth: Resource<String> = Resource.loading(),
)