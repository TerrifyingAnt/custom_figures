package xd.jg.custom_figures.presentation.FigureDetailScreen

import xd.jg.custom_figures.data.dto.FigureDto
import xd.jg.custom_figures.utils.Resource

/**
 * @param figureDetails хранит информацию о фигурке
 * */
data class FigureDetailUIState (
    val figureDetails: Resource<FigureDto> = Resource.loading()
)