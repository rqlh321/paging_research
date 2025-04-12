package ru.gubatenko.user.domain.impl

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import ru.gubatenko.auth.data.CreateAccountBody
import ru.gubatenko.auth.data.CreateAccountRout
import ru.gubatenko.auth.data.DeleteUserBody
import ru.gubatenko.auth.data.DeleteUserRout
import ru.gubatenko.common.BadRequestResponseStatus

class UserApi(
    private val httpClient: HttpClient
) {

    suspend fun create(body: CreateAccountBody) {
        val httpResponse = httpClient.post(CreateAccountRout) { setBody(body) }
        if (!httpResponse.status.isSuccess()) {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }

    suspend fun delete(
        body: DeleteUserBody = DeleteUserBody,
        rout: DeleteUserRout = DeleteUserRout
    ) {
        val httpResponse = httpClient.post(rout) { setBody(body) }
        if (!httpResponse.status.isSuccess()) {
            throw BadRequestResponseStatus(httpResponse.status)
        }
    }
}