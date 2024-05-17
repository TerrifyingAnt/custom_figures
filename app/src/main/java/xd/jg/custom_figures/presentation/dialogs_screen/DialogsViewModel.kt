package xd.jg.custom_figures.presentation.dialogs_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xd.jg.custom_figures.domain.remote.IChatRepository
import javax.inject.Inject

@HiltViewModel
class DialogsViewModel @Inject constructor(
    private val iChatRepository: IChatRepository
): ViewModel() {

    private val _dialogsUIState = mutableStateOf(DialogsUIState())
    val dialogsUIState: State<DialogsUIState> = _dialogsUIState

    /** состояние всего*/
    private fun updateUIState(update: DialogsUIState.() -> DialogsUIState) {
        _dialogsUIState.value = _dialogsUIState.value.update()
    }

    fun getDialogs() = viewModelScope.launch {
        val response = iChatRepository.getChats()
        if (response.data != null) {
            updateUIState {
                copy (
                    dialogsList = response
                )
            }
        }
    }


}