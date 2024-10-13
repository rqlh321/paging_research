package ru.gubatenko.server_api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

internal val androidClient = HttpClient(Android) {
    defaultRequest {
        contentType(ContentType.Application.Json)
        url("http://172.17.0.1:8080/")
    }
    install(ContentNegotiation) { json() }
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
        filter { request -> request.url.host.contains("http://172.17.0.1:8080/") }
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
}