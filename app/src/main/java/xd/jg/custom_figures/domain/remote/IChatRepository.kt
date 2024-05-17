package xd.jg.custom_figures.domain.remote

import xd.jg.custom_figures.data.dto.DialogDto
import xd.jg.custom_figures.data.dto.MessageDto
import xd.jg.custom_figures.utils.Resource

interface IChatRepository {

    suspend fun getChats(): Resource<List<DialogDto>>

    suspend fun getChatById(chatId: Int): Resource<List<MessageDto>>
}