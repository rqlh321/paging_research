package ru.gubatenko.server.plugins

import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.AuthRout
import ru.gubatenko.server.data.DataStore

fun Routing.auth(
    dataStore: DataStore
) {
    post<AuthRout, AuthBody> { _, body -> call.respond(body) }
}