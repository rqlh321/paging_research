package ru.gubatenko.server.plugins

import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.html.respondHtml
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.gubatenko.common.Chats
import ru.gubatenko.common.UserId
import ru.gubatenko.server.appModule
import ru.gubatenko.server.data.DataStore
import ru.gubatenko.server.domain.CreateMessageUseCase
import ru.gubatenko.server.session.UserWebSocketSessionController
import kotlin.time.Duration.Companion.milliseconds

fun Application.configureRouting() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(Resources)
    install(ContentNegotiation) { json() }
    install(WebSockets) {
        pingPeriod = 15000.milliseconds
        timeout = 15000.milliseconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)

    }
    val sessionController by inject<UserWebSocketSessionController>()
    val dataStore by inject<DataStore>()
    val createMessageUseCase by inject<CreateMessageUseCase>()

    routing {
        get("") {
            val chats = dataStore.chats() as Chats

            call.respondHtml {
                head {
                    title {
                        +"Chats"
                    }
                }
                body {
                    chats.items.forEach {
                        p { h1 { +it.name } }
                    }
                }
            }
        }
        auth()
        chats(dataStore)
        messages(dataStore, createMessageUseCase)

        webSocket {
            sessionController.checkInSession(
                id = call.request.headers[UserId.KEY],
                newSession = this
            )
        }
    }
}