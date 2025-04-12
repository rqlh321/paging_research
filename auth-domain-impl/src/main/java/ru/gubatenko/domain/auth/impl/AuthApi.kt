package ru.gubatenko.domain.auth.impl

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
import ru.gubatenko.common.BadRequestResponseStatus

class AuthApi(
    private val httpClient: HttpClient
) {

    suspend fun login(body: LoginBody): Credentials {
        val httpResponse = httpClient.post(LoginRout) { setBody(body) }
        if (httpResponse.status.isSuccess()) {
            return httpResponse.body<Credentials>()
        } else {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }

    suspend fun logout(
        body: LogoutUserBody = LogoutUserBody,
        rout: LogoutUserRout = LogoutUserRout
    ) = httpClient.post(rout) { setBody(body) }

}