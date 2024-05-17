package xd.jg.custom_figures.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import xd.jg.custom_figures.data.dto.DialogDto
import xd.jg.custom_figures.data.dto.MessageDto

interface IChatClient {
    @GET("/chats")
    suspend fun getChats(): Response<List<DialogDto>>

    @GET("/chats/{id}")
    suspend fun getChatById(@Path(value = "id", encoded = true) chatId: Int): Response<List<MessageDto>>
}