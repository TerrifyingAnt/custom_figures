package xd.jg.custom_figures.data.repository.remote

import xd.jg.custom_figures.data.dto.DialogDto
import xd.jg.custom_figures.data.dto.MessageDto
import xd.jg.custom_figures.data.remote.BaseDataSource
import xd.jg.custom_figures.data.remote.IChatClient
import xd.jg.custom_figures.domain.remote.IChatRepository
import xd.jg.custom_figures.utils.Resource
import xd.jg.custom_figures.utils.Utils.getDialogs
import xd.jg.custom_figures.utils.Utils.getMessagesByDialog
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val iChatClient: IChatClient
): BaseDataSource(), IChatRepository {
    override suspend fun getChats(): Resource<List<DialogDto>> {
       return Resource.success(getDialogs())
    }

    override suspend fun getChatById(chatId: Int): Resource<List<MessageDto>> {
        return Resource.success(getMessagesByDialog())
    }

}