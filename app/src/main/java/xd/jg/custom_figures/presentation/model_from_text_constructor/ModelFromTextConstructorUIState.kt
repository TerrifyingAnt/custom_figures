package xd.jg.custom_figures.presentation.model_from_text_constructor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ModelFromTextConstructorUIState (
    val figureDescription: MutableState<String> = mutableStateOf(""),
    val figureReferences: MutableState<String> = mutableStateOf(""),
    val switchColorFigure: MutableState<Boolean> = mutableStateOf(false),
    val switchMovingFigure: MutableState<Boolean> = mutableStateOf(false),

    val error: MutableState<Boolean> = mutableStateOf(false),
    val successfullyAdded: MutableState<Boolean> = mutableStateOf(false)
)