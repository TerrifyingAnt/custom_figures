package xd.jg.custom_figures.presentation.profile_screen

import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.utils.Resource

data class ProfileUIState (
    val userInfo: Resource<UserInfoDto> = Resource.loading()
)