package ru.gubatenko.server.plugins

import io.ktor.resources.Resource
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.server.data.DataStore

fun Routing.chats(
    dataStore: DataStore
) = route(ApiRouts.CHATS) {
    post<CreateChatRout, CreateChatBody> { _, body -> call.respond(dataStore.createChat(body)) }
    get<ChatRout> { call.respond(dataStore.chats()) }
}

@Resource("/")
data object ChatRout

@Resource("/")
data object CreateChatRout
