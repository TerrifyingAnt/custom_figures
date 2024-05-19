package xd.jg.custom_figures.presentation.chat_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.data.dto.MessageDto
import xd.jg.custom_figures.domain.remote.IChatRepository
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val iChatRepository: IChatRepository
): ViewModel() {

    private val _chatUIState = mutableStateOf(ChatUIState())
    val chatUIState: State<ChatUIState> = _chatUIState

    /** состояние всего*/
    private fun updateUIState(update: ChatUIState.() -> ChatUIState) {
        _chatUIState.value = _chatUIState.value.update()
    }

    fun getMessages(chatId: Int) = viewModelScope.launch {
        val response = iChatRepository.getChatById(chatId)
        if (response.data != null) {
            updateUIState {
                copy (
                    messages = response.data.toMutableStateList()
                )
            }
        }
    }

    fun updateMessage(newValue: String) {
        updateUIState {
            copy (
                currentMessage = mutableStateOf(newValue)
            )
        }
    }

    fun sendMessage() {
        chatUIState.value.messages.add(
            MessageDto(
                messageData = chatUIState.value.currentMessage.value,
                fromMe = true,
                date = System.currentTimeMillis().toString()
            )
        )
        updateUIState {
            copy(
                currentMessage = mutableStateOf("")
            )
        }
    }

}