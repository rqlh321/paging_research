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
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import ru.gubatenko.common.Response
import ru.gubatenko.credential.store.TokenStore

class Client(
    private val config: ClientConfig = ClientConfig(),
    private val tokenStore: TokenStore
) {
    val httpClient = HttpClient(OkHttp) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
            pingInterval = config.wsPingTimeOut
        }

        defaultRequest {
            header(
                AUTH_HEADER_KEY,
                AUTH_HEADER_VAL.format(tokenStore.getAccessToken()?.value.orEmpty())
            )
            contentType(ContentType.Application.Json)
            url(config.host)
            port = config.port
        }
        install(HttpTimeout) {
            requestTimeoutMillis = config.requestTimeout
            connectTimeoutMillis = config.connectTimeout
        }
        install(Resources)
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
            filter { request -> request.url.host.contains(config.host) }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    val socketFlow = callbackFlow {
        httpClient.webSocket {
            send(receiveDeserialized<Response>())
            awaitCancellation()
        }
    }
}


const val AUTH_HEADER_KEY = "Authorization"
const val AUTH_HEADER_VAL = "Bearer %s"
