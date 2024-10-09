package com.example.paging_reserch.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.gubatenko.common.Chat
import ru.gubatenko.common.CreateChat
import ru.gubatenko.common.Response

class ServerApi {
    private val client = HttpClient(Android) {
        defaultRequest {
            url("http://172.17.0.1:8080/")
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            filter { request -> request.url.host.contains("http://172.17.0.1:8080/") }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    suspend fun getAllChats() = client.get("chats").body<Response<List<Chat>>>()
    suspend fun createChat(name: String) = client.post("chats/create") {
        contentType(ContentType.Application.Json)
        setBody(CreateChat(name))
    }
}