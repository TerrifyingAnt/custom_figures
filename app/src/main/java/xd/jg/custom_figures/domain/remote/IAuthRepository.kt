package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.dto.TokensDto
import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.data.dto.UserRegisterInfoDto
import xd.jg.custom_figures.utils.Resource

interface IAuthRepository {
    suspend fun login(login: String, password: String): Resource<TokensDto>

    suspend fun register(user: UserRegisterInfoDto)

    suspend fun getMe(): Resource<UserInfoDto>
}