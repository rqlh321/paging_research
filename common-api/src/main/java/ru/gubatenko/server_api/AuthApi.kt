package ru.gubatenko.server_api

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.AuthRout
import ru.gubatenko.common.Credentials

class AuthApi {

    suspend fun auth(
        body: AuthBody,
        rout: AuthRout = AuthRout
    ) = httpClient.post(rout) { setBody(body) }
        .body<Credentials>()

}