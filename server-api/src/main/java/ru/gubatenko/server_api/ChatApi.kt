package ru.gubatenko.server_api

import io.ktor.client.call.body
import io.ktor.client.request.setBody
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.Response

class ChatApi : ServerApi() {

    override val rout: String = ApiRouts.CHATS

    suspend fun chats() = get().body<Response<List<Chat>>>().result

    suspend fun create(
        body: CreateChatBody
    ) = post { setBody(body) }.body<Response<ChatId>>().result

}