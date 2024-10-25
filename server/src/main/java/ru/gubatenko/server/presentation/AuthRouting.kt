package ru.gubatenko.server.presentation

import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.AuthRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.LoginUseCase

class AuthRouting(
    private val loginUseCase: LoginUseCase
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.post<AuthRout, AuthBody> { _, body -> call.respond(loginUseCase.run(body)) }
    }
}