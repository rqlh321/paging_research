package ru.gubatenko.server.presentation

import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.auth.data.AuthRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.usecase.LoginUseCase

class AuthRouting(
    private val loginUseCase: LoginUseCase
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.post<AuthRout, AuthBody> { _, body -> call.respond(loginUseCase(body)) }
    }
}