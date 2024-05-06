package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.utils.Resource

interface IProfileRepository {
    suspend fun getMe(): Resource<UserInfoDto>
}