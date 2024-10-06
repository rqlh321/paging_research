package ru.gubatenko.server.plugins

import io.ktor.resources.Resource
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import ru.gubatenko.common.ChatId
import ru.gubatenko.server.data.DataStore

fun Application.configureRouting(dataStore: DataStore) {
    install(Resources)
    install(ContentNegotiation) {
        json()
    }
    routing {
        post<ChatsRout.Create> { call.respond(dataStore.createChat(it.name)) }
        get<ChatsRout> { call.respond(dataStore.chats()) }

        post<MessagesRout.Create> { call.respond(dataStore.createMessage(it.chatId, it.text)) }
        get<MessagesRout> { call.respond(dataStore.messages(it.chatId)) }
    }
}


@Serializable
@Resource("/chats")
data object ChatsRout {
    @Resource("create")
    class Create(val name: String)
}

@Serializable
@Resource("/messages")
data class MessagesRout(
    val chatId: ChatId
) {
    @Resource("create")
    class Create(
        val chatId: ChatId,
        val text: String
    )
}