package xd.jg.custom_figures.presentation.model_from_text_constructor

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.domain.local.IBasketRepository
import javax.inject.Inject

@HiltViewModel
class ModelFromTextConstructorViewModel @Inject constructor(
    private val db: IBasketRepository
): ViewModel() {

    private val _modelFromPhotoConstructorUIState = mutableStateOf(ModelFromTextConstructorUIState())
    val modelFromPhotoConstructorUIState: State<ModelFromTextConstructorUIState> = _modelFromPhotoConstructorUIState

    /** состояние всего*/
    private fun updateUIState(update: ModelFromTextConstructorUIState.() -> ModelFromTextConstructorUIState) {
        _modelFromPhotoConstructorUIState.value = _modelFromPhotoConstructorUIState.value.update()
    }

    fun updateDescription(newValue: String) {
        updateUIState {
            copy (
                figureDescription = mutableStateOf(newValue)
            )
        }
    }

    fun updateReferencesLink(newValue: String) {
        updateUIState {
            copy (
                figureReferences = mutableStateOf(newValue)
            )
        }
    }

    fun updateColorFigureSwitch() {
        updateUIState {
            copy(
                switchColorFigure = mutableStateOf(!switchColorFigure.value)
            )
        }
    }

    fun updateMovableFigureSwitch() {
        updateUIState{
            copy(
                switchMovingFigure = mutableStateOf(!switchMovingFigure.value)
            )
        }
    }

    fun updateError(state: Boolean) {
        updateUIState {
            copy (
                error = mutableStateOf(state)
            )
        }
    }

    fun saveDescription() = viewModelScope.launch {
        if (modelFromPhotoConstructorUIState.value.figureDescription.value != "" && modelFromPhotoConstructorUIState.value.figureReferences.value != "") {
            db.insertFigureToBasket(
                BasketItemEntity(
                    id = 0,
                    type = FigureType.CUSTOM_BY_TEXT.ordinal,
                    description = modelFromPhotoConstructorUIState.value.figureDescription.value,
                    references = modelFromPhotoConstructorUIState.value.figureReferences.value,
                    colored = modelFromPhotoConstructorUIState.value.switchColorFigure.value,
                    movable = modelFromPhotoConstructorUIState.value.switchMovingFigure.value
                )
            )
            updateUIState {
                copy (
                    successfullyAdded = mutableStateOf(true)
                )
            }
        }
        else {
            updateError(true)
        }
    }
}
