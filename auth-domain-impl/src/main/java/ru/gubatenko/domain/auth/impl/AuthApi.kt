package ru.gubatenko.domain.auth.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.auth.data.CreateAccountRout
import ru.gubatenko.auth.data.Credentials
import ru.gubatenko.auth.data.LoginRout
import ru.gubatenko.common.BadRequestResponseStatus

class AuthApi(
    private val httpClient: HttpClient
) {

    suspend fun login(body: AuthBody): Credentials {
        val httpResponse = httpClient.post(LoginRout) { setBody(body) }
        if (httpResponse.status.isSuccess()) {
            return httpResponse.body<Credentials>()
        } else {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }

    suspend fun create(body: AuthBody): Credentials {
        val httpResponse = httpClient.post(CreateAccountRout) { setBody(body) }
        if (httpResponse.status.isSuccess()) {
            return httpResponse.body<Credentials>()
        } else {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }
}