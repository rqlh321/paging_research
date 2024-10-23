package ru.gubatenko.server_api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import ru.gubatenko.common.Response
import ru.gubatenko.common.UserId

private const val BASE_HOST = "http://192.168.0.4"
private const val PORT = 8080
private const val CONNECT_TIMEOUT = 5000L
private const val REQUEST_TIMEOUT = 10000L
private const val WS_PING_INTERVAL = 15000L
internal val httpClient = HttpClient(OkHttp) {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
        pingInterval = WS_PING_INTERVAL
    }
    defaultRequest {
        contentType(ContentType.Application.Json)
        url(BASE_HOST)
        port = PORT
    }
    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT
        connectTimeoutMillis = CONNECT_TIMEOUT
    }
    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
        filter { request -> request.url.host.contains(BASE_HOST) }
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
}

fun socketFlow() = callbackFlow {
    httpClient.webSocket(request = { header(UserId.KEY, "test") }) {
        while (isActive) {
            val response = receiveDeserialized<Response>()
            send(response)
        }
        awaitClose()
    }
}
