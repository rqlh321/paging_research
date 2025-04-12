package ru.gubatenko.chat.domain.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import ru.gubatenko.chat.data.Chat
import ru.gubatenko.chat.data.ChatRout
import ru.gubatenko.chat.data.Chats
import ru.gubatenko.chat.data.CreateChatBody
import ru.gubatenko.chat.data.CreateChatRout

class ChatApiImpl(
    private val httpClient: HttpClient
) {

    suspend fun chats(
        rout: ChatRout = ChatRout
    ) = httpClient.get(rout)
        .body<Chats>()

    suspend fun create(
        body: CreateChatBody,
        rout: CreateChatRout = CreateChatRout
    ) = httpClient.post(rout) { setBody(body) }
        .body<Chat>()

}