package ru.gubatenko.domain.auth.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.auth.data.AuthRout
import ru.gubatenko.auth.data.Credentials

class AuthApi(
    private val httpClient: HttpClient
) {

    suspend fun auth(
        body: AuthBody,
        rout: AuthRout = AuthRout
    ): Credentials {
        val httpResponse = httpClient.post(rout) { setBody(body) }
        if (httpResponse.status.isSuccess()) {
            return httpResponse.body<Credentials>()
        } else {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }
}