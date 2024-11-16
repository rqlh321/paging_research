package ru.gubatenko.server.presentation

import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.message.data.CreateMessageBody
import ru.gubatenko.message.data.CreateMessageRout
import ru.gubatenko.message.data.MessagesRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.repo.MessageRepository
import ru.gubatenko.server.domain.usecase.CreateMessageUseCase

class MessagesRouting(
    private val repo: MessageRepository,
    private val createMessageUseCase: CreateMessageUseCase,
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.authenticate {
            get<MessagesRout> {
                val userId = call.userId()
                val result = repo.messages(userId, it)
                call.respond(result)
            }
            post<CreateMessageRout, CreateMessageBody> { _, body ->
                val userId = call.userId()
                val args = CreateMessageUseCase.Args(userId, body)
                val result = createMessageUseCase(args)
                call.respond(result)
            }
        }
    }
}