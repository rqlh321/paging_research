package ru.gubatenko.message.domain.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import ru.gubatenko.message.data.CreateMessageBody
import ru.gubatenko.message.data.CreateMessageRout
import ru.gubatenko.message.data.Message
import ru.gubatenko.message.data.Messages
import ru.gubatenko.message.data.MessagesRout

class MessageApi(
    private val httpClient: HttpClient
) {

    suspend fun messages(
        rout: MessagesRout
    ) = httpClient.get(rout)
        .body<Messages>()

    suspend fun create(
        body: CreateMessageBody,
        rout: CreateMessageRout = CreateMessageRout
    ) = httpClient.post(rout) { setBody(body) }
        .body<Message>()

}