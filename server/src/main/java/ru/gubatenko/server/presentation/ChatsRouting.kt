package ru.gubatenko.server.presentation

import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.common.ChatRout
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateChatRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.ChatRepository

class ChatsRouting(
    private val repo: ChatRepository
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.authenticate {
            get<ChatRout> {
                val userId = call.userId()
                val result = repo.chats(userId)
                call.respond(result)
            }
            post<CreateChatRout, CreateChatBody> { _, body ->
                val userId = call.userId()
                val result = repo.createChat(userId, body)
                call.respond(result)
            }
        }
    }
}