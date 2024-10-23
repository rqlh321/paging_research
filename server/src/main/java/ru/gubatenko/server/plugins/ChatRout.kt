package ru.gubatenko.server.plugins

import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.common.ChatRout
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateChatRout
import ru.gubatenko.server.data.DataStore

fun Routing.chats(dataStore: DataStore) {
    get<ChatRout> { call.respond(dataStore.chats()) }
    post<CreateChatRout, CreateChatBody> { _, body -> call.respond(dataStore.createChat(body)) }
}