package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.dto.ModelPartListDto
import xd.jg.custom_figures.utils.Resource
import java.io.File

interface IFigureRepository {
    suspend fun sendImage(imageFile: File): Resource<ModelPartListDto>
}