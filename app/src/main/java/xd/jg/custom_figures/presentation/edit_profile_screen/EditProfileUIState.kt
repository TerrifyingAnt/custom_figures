package xd.jg.custom_figures.presentation.edit_profile_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.utils.Resource

data class EditProfileUIState (
    val newUserName: MutableState<String> = mutableStateOf(""),
    val newUserEmail: MutableState<String> = mutableStateOf(""),
    val newUserPhone: MutableState<String> = mutableStateOf(""),

    val oldUserName: MutableState<String> = mutableStateOf(""),
    val oldUserEmail: MutableState<String> = mutableStateOf(""),
    val oldUserPhone: MutableState<String> = mutableStateOf(""),

    val avatarSourcePath: MutableState<String> = mutableStateOf(""),

    val avatarUpdated: Resource<String> = Resource.loading(),
    val nameUpdated: Resource<String> = Resource.loading(),
    val phoneUpdated: Resource<String> = Resource.loading()

)