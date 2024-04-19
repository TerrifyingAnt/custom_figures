package xd.jg.custom_figures.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TagFilter(
    val tagTitle: String,
    val isPressed: MutableState<Boolean> = mutableStateOf(false)
)
