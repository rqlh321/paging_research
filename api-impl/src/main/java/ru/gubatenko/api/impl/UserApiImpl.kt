package ru.gubatenko.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import ru.gubatenko.auth.data.CreateAccountBody
import ru.gubatenko.auth.data.CreateAccountRout
import ru.gubatenko.auth.data.DeleteUserBody
import ru.gubatenko.auth.data.DeleteUserRout
import ru.gubatenko.common.BadRequestResponseStatus
import ru.gubatenko.user.remote.api.UserApi

class UserApiImpl(
    private val httpClient: HttpClient
) : UserApi {

    override suspend fun create(body: CreateAccountBody) {
        val httpResponse = httpClient.post(CreateAccountRout) { setBody(body) }
        if (!httpResponse.status.isSuccess()) {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }

    override suspend fun delete() {
        val httpResponse = httpClient.post(DeleteUserRout) { setBody(DeleteUserBody) }
        if (!httpResponse.status.isSuccess()) {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }
}