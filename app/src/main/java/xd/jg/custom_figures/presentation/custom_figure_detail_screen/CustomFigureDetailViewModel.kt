package xd.jg.custom_figures.presentation.custom_figure_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.domain.local.IBasketRepository
import javax.inject.Inject

@HiltViewModel
class CustomFigureDetailViewModel @Inject constructor(
    private val db: IBasketRepository
): ViewModel() {

    private val _figureCustomDetailUIState = mutableStateOf(CustomFigureDetailUIState())
    val figureCustomDetailUIState: State<CustomFigureDetailUIState> = _figureCustomDetailUIState

    private fun updateUIState(update: CustomFigureDetailUIState.() -> CustomFigureDetailUIState) {
        _figureCustomDetailUIState.value = _figureCustomDetailUIState.value.update()
    }

    fun getFigureDetails(basketId: Int) = viewModelScope.launch {
        val figure = db.getFigureByBasketId(basketId)
        updateUIState {
            copy (
                currentFigure = mutableStateOf(figure)
            )
        }
    }

}