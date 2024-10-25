package ru.gubatenko.server.presentation

import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.CreateMessageRout
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.data.DataStore
import ru.gubatenko.server.domain.CreateMessageUseCase

class MessagesRouting(
    private val dataStore: DataStore,
    private val createMessageUseCase: CreateMessageUseCase,
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.authenticate {
            get<MessagesRout> { call.respond(dataStore.messages(it)) }
            post<CreateMessageRout, CreateMessageBody> { _, body ->
                call.respond(createMessageUseCase.run(body))
            }
        }
    }
}