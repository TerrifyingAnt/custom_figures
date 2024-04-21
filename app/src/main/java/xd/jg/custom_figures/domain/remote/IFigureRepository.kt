package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.data.dto.TagTitleDto
import xd.jg.custom_figures.utils.Resource

interface IFigureRepository {

    /** Для получения превьюшной информации о фигурке с сервера */
    suspend fun getFigures(filters: List<String>, figureTitle: String, page: Int): Resource<List<FigurePreviewDto>>

    /** Для получение тэгов с сервера */
    suspend fun getTags(): Resource<List<TagTitleDto>>

    suspend fun getFigureById(figureId: Int): Resource<FigureDto>
}