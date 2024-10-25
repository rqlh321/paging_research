package ru.gubatenko.server.presentation

import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.Routing
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.close
import kotlinx.coroutines.awaitCancellation
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.data.UserWebSocketSessionController
import ru.gubatenko.server.domain.LoginUseCase

class WebSocketRouting(
    private val sessionController: UserWebSocketSessionController
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.authenticate {
            webSocket {
                val username = call.principal<JWTPrincipal>()
                    ?.payload
                    ?.getClaim(LoginUseCase.claim)
                    ?.asString()
                if (username == null) {
                    close()
                } else {
                    sessionController.checkInSession(
                        id = username,
                        newSession = this
                    )
                    awaitCancellation()
                }
            }
        }
    }
}