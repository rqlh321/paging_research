package ru.gubatenko.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import ru.gubatenko.auth.data.Credentials
import ru.gubatenko.auth.data.LoginBody
import ru.gubatenko.auth.data.LoginRout
import ru.gubatenko.auth.data.LogoutUserBody
import ru.gubatenko.auth.data.LogoutUserRout
import ru.gubatenko.auth.remote.api.AuthApi
import ru.gubatenko.common.BadRequestResponseStatus

class AuthApiImpl(
    private val httpClient: HttpClient
) : AuthApi {

    override suspend fun login(body: LoginBody): Credentials {
        val httpResponse = httpClient.post(LoginRout) { setBody(body) }
        if (httpResponse.status.isSuccess()) {
            return httpResponse.body<Credentials>()
        } else {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }

    override suspend fun logout() {
        val httpResponse = httpClient.post(LogoutUserRout) { setBody(LogoutUserBody) }
        if (!httpResponse.status.isSuccess()) {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }
}