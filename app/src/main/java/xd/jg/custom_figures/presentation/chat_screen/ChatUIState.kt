package xd.jg.custom_figures.presentation.chat_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import xd.jg.custom_figures.data.dto.MessageDto

data class ChatUIState(
    val messages: SnapshotStateList<MessageDto> = mutableStateListOf(),
    val currentMessage: MutableState<String> = mutableStateOf("")
)