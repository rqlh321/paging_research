package ru.gubatenko.server.plugins

import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.CreateMessageRout
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server.data.DataStore

fun Routing.messages(
    dataStore: DataStore
) {
    get<MessagesRout> { call.respond(dataStore.messages(it)) }

    post<CreateMessageRout, CreateMessageBody> { _, body ->
        call.respond(dataStore.createMessage(body))
    }
}