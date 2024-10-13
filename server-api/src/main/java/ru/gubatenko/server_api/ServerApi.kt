package ru.gubatenko.server_api

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post

abstract class ServerApi(
    private val client: HttpClient = androidClient
) {

    protected abstract val rout: String
    protected suspend fun get() = client.get(rout)
    protected suspend fun post(block: HttpRequestBuilder.() -> Unit = {}) = client.post(rout, block)

}