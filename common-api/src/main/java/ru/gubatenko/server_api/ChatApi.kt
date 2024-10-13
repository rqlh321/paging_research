package ru.gubatenko.server_api

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatRout
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateChatRout

class ChatApi {

    suspend fun all(
        rout: ChatRout = ChatRout
    ) = httpClient.get(rout)
        .body<List<Chat>>()

    suspend fun create(
        body: CreateChatBody,
        rout: CreateChatRout = CreateChatRout
    ) = httpClient.post(rout) { setBody(body) }
        .body<Chat>()

}