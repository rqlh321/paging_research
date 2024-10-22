package ru.gubatenko.server.plugins

import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import kotlinx.serialization.json.Json
import ru.gubatenko.common.UserId
import ru.gubatenko.server.data.DataStoreHashMaps
import ru.gubatenko.server.domain.CreateMessageUseCase
import ru.gubatenko.server.session.UserWebSocketSessionController
import kotlin.time.Duration.Companion.milliseconds


fun Application.configureRouting() {
    val dataStore = DataStoreHashMaps()
    val sessionController = UserWebSocketSessionController()
    val useCase = CreateMessageUseCase(dataStore, sessionController)
    install(Resources)
    install(ContentNegotiation) { json() }
    install(WebSockets) {
        pingPeriod = 15000.milliseconds
        timeout = 15000.milliseconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)

    }
    routing {
        auth(dataStore)
        chats(dataStore)
        messages(dataStore, useCase)

        webSocket {
            sessionController.checkInSession(
                id = call.request.headers[UserId.KEY],
                newSession = this
            )
        }
    }
}