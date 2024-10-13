package ru.gubatenko.server.plugins

import io.ktor.resources.Resource
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.server.data.DataStore

fun Routing.messages(
    dataStore: DataStore
) = route(ApiRouts.MESSAGES) {
    post<CreateMessageRout, CreateMessageBody> { _, body ->
        call.respond(dataStore.createMessage(body))
    }
    get<MessagesRout> { call.respond(dataStore.messages(it.chatId)) }
}

@Resource("/")
data class MessagesRout(val chatId: ChatId)

@Resource("/")
data object CreateMessageRout