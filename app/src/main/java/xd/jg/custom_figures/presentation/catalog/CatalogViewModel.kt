package xd.jg.custom_figures.presentation.catalog

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.domain.model.TagFilter
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.toTagFilter
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val iFigureRepository: IFigureRepository
): ViewModel() {

    private val _catalogUIState = mutableStateOf(CatalogUIState())
    val catalogUIState: State<CatalogUIState> = _catalogUIState

    private fun updateUIState(update: CatalogUIState.() -> CatalogUIState) {
        _catalogUIState.value = _catalogUIState.value.update()
    }

    /** Метод получает фигурки с сервера по парамтрам*/
    fun getFigures() = viewModelScope.launch {
        updateUIState { copy(
            currentUIState = Resource.loading()
        ) }
        Log.d("PARAMS", _catalogUIState.value.currentFiltersTag.data
            ?.filter { it.isPressed.value }
            ?.map { it.tagTitle }.toString() + " " + _catalogUIState.value.currentFilterTitle + " " + _catalogUIState.value.currentPage)
        val response = iFigureRepository.getFigures(
            _catalogUIState.value.currentFiltersTag.data
                ?.filter { it.isPressed.value }
                ?.map { it.tagTitle } ?: listOf(),
            _catalogUIState.value.currentFilterTitle.value,
            _catalogUIState.value.currentPage)
        if (response.data != null) {
            updateUIState {
                copy(
                    currentUIState = Resource.success(response.data)
                )
            }
        }
    }

    /** Метод получает все теги с сервера */
    fun getTags() = viewModelScope.launch {
        updateUIState { copy(
            currentFiltersTag = Resource.loading()
        ) }
        val response = iFigureRepository.getTags()
        if (response.data != null) {
            updateUIState {
                copy(
                    currentFiltersTag = Resource.success(response.data.map {it.toTagFilter()})
                )
            }
        }
    }

    /** Метод обновляет фигурки при нажатии*/
    fun updateIsPressedFilter(tagFilter: TagFilter) {
        val currentTags = _catalogUIState.value.currentFiltersTag.data
        currentTags?.forEach {
            if (it.tagTitle == tagFilter.tagTitle) {
                it.isPressed.value = !it.isPressed.value
            }
        }
        updateUIState {
            copy(
                currentFiltersTag = Resource.success(currentTags)
            )
        }
        getFigures()
    }

    /** Метод обновляет фигурки по текстовому поиску*/
    fun updateTitleFilter(newValue: String) {
        updateUIState {
            copy (
                currentFilterTitle = mutableStateOf(newValue)
            )
        }
        getFigures()
    }
}