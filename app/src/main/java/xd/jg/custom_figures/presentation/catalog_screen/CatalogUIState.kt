package xd.jg.custom_figures.presentation.catalog_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xd.jg.custom_figures.data.dto.FigurePreviewDto
import xd.jg.custom_figures.domain.model.TagFilter
import xd.jg.custom_figures.utils.Resource

data class CatalogUIState (
    val currentFiltersTag: Resource<List<TagFilter>> = Resource.loading(),
    val currentFilterTitle: MutableState<String> = mutableStateOf(""),
    val currentPage: Int = 0,
    val currentUIState: Resource<List<FigurePreviewDto>> = Resource.loading(),
    val isDialogShown: MutableState<Boolean> = mutableStateOf(false)
)