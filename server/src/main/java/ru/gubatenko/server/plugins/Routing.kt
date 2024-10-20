package ru.gubatenko.server.plugins

import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import ru.gubatenko.common.Chat
import ru.gubatenko.common.Message
import ru.gubatenko.common.Response
import ru.gubatenko.server.data.DataStore
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.milliseconds


fun Application.configureRouting(dataStore: DataStore) {
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
        messages(dataStore)

        webSocket {
            println(call.request.headers.entries())
            while (isActive) {
                delay(1000)
                sendSerialized<Response>(Message.test())
                sendSerialized<Response>(Chat.test())
            }
        }
    }
}