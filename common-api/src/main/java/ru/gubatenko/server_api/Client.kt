package ru.gubatenko.server_api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

private const val BASE = "http://192.168.0.4:8080/"
private const val CONNECT_TIMEOUT = 5000L
private const val REQUEST_TIMEOUT = 10000L
internal val httpClient = HttpClient(Android) {
    defaultRequest {
        contentType(ContentType.Application.Json)
        url(BASE)
    }
    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT
        connectTimeoutMillis = CONNECT_TIMEOUT
    }
    install(Resources)
    install(ContentNegotiation) { json() }
    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
        filter { request -> request.url.host.contains(BASE) }
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
}