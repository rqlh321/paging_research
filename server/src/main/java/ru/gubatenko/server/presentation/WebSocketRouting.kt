package ru.gubatenko.server.presentation

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.awaitCancellation
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.data.UserWebSocketSessionController

class WebSocketRouting(
    private val sessionController: UserWebSocketSessionController,
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.authenticate {
            webSocket {
                val userId = call.userId()
                sessionController.checkInSession(
                    userId = userId,
                    newSession = this
                )
                awaitCancellation()
            }
        }
    }
}