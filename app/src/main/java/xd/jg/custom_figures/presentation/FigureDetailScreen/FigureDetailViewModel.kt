package xd.jg.custom_figures.presentation.FigureDetailScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.domain.remote.IFigureRepository
import xd.jg.custom_figures.utils.Resource
import javax.inject.Inject

@HiltViewModel
class FigureDetailViewModel @Inject constructor (
    private val iFigureRepository: IFigureRepository
): ViewModel() {

    private val _figureDetailUIState = mutableStateOf(FigureDetailUIState())
    val figureDetailUIState: State<FigureDetailUIState> = _figureDetailUIState

    private fun updateUIState(update: FigureDetailUIState.() -> FigureDetailUIState) {
        _figureDetailUIState.value = _figureDetailUIState.value.update()
    }

    /** Метод получает информацию о фигурке по ее id*/
    fun getFigureInfo(figureId: Int) = viewModelScope.launch {
        val response = iFigureRepository.getFigureById(figureId)
        if (response.data != null) {
            updateUIState {
                copy(
                    figureDetails = Resource.success(response.data)
                )
            }
        }
        else {
            updateUIState {
                copy(
                    figureDetails = Resource.error(response.message?: "")
                )
            }
        }
    }
}