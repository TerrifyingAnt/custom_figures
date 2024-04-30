package xd.jg.custom_figures.data.repository.remote

import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.dto.TagTitleDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IFigureClient
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

class FigureRepositoryImpl @Inject constructor(
    private val iFigureClient: IFigureClient
) : BaseDataSource(), IFigureRepository {
        override suspend fun getFigures(filters: List<String>, figureTitle: String, page: Int): Resource<List<FigurePreviewDto>> {
        return safeApiCall { iFigureClient.getFigures(filters, figureTitle, page) }
    }

    override suspend fun getTags(): Resource<List<TagTitleDto>> {
        return safeApiCall {iFigureClient.getTags()}
    }

    override suspend fun getFigureById(figureId: Int): Resource<FigureDto> {
        return safeApiCall { iFigureClient.getFigureById(figureId)}
    }

    override suspend fun getFigurePreviewByIds(figureIds: List<Int>, page: Int): Resource<List<FigurePreviewDto>> {
            return safeApiCall { iFigureClient.getFigurePreviewByIds(figureIds, page) }
        }
}
