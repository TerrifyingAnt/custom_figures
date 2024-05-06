package xd.jg.custom_figures.data.repository.remote

import xd.jg.custom_figures.data.dto.TokensDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IAuthClient
import xd.jg.custom_figures.domain.remote.IAuthRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val iAuthClient: IAuthClient
) : BaseDataSource(), IAuthRepository {
    override suspend fun login(login: String, password: String): Resource<TokensDto> {
        return safeApiCall { iAuthClient.login(login, password) }
    }

}