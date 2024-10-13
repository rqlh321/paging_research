package ru.gubatenko.server_api

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.CreateMessageRout
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessagesRout

class MessageApi {

    suspend fun all(
        rout: MessagesRout
    ) = httpClient.get(rout)
        .body<List<Message>>()

    suspend fun create(
        body: CreateMessageBody,
        rout: CreateMessageRout = CreateMessageRout
    ) = httpClient.post(rout) { setBody(body) }
        .body<Message>()

}