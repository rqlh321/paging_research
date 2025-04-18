package ru.gubatenko.server.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.auth.data.LoginBody
import ru.gubatenko.auth.data.LoginRout
import ru.gubatenko.auth.data.LogoutUserBody
import ru.gubatenko.auth.data.LogoutUserRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.usecase.LoginUseCase

class AuthRouting(
    private val loginUseCase: LoginUseCase,
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.post<LoginRout, LoginBody> { _, body ->
            val response = loginUseCase(body)
            if (response == null) {
                call.respond(HttpStatusCode.Gone)
            } else {
                call.respond(response)
            }
        }
        routing.authenticate {
            post<LogoutUserRout, LogoutUserBody> { _, _ ->
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
