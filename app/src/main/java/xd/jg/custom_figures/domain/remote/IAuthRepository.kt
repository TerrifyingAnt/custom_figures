package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.dto.TokensDto
import xd.jg.custom_figures.utils.Resource

interface IAuthRepository {
    suspend fun login(login: String, password: String): Resource<TokensDto>
}