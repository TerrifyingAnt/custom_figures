package xd.jg.custom_figures.presentation.dialogs_screen

import xd.jg.custom_figures.data.dto.DialogDto
import xd.jg.custom_figures.utils.Resource

data class DialogsUIState (
    val dialogsList: Resource<List<DialogDto>> = Resource.loading()
)