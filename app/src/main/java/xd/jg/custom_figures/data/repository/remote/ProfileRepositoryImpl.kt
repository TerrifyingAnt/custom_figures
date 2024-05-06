package xd.jg.custom_figures.data.repository.remote

import xd.jg.custom_figures.data.dto.UserInfoDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IProfileClient
import xd.jg.custom_figures.domain.remote.IProfileRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val iProfileClient: IProfileClient
) : BaseDataSource(), IProfileRepository {
    override suspend fun getMe(): Resource<UserInfoDto> {
        return safeApiCall { iProfileClient.getMe() }
    }

}