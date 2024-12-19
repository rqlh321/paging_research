package ru.gubatenko.server.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.auth.data.CreateAccountRout
import ru.gubatenko.auth.data.LoginRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.repo.UserRepository
import ru.gubatenko.server.domain.usecase.LoginUseCase

class AuthRouting(
    private val loginUseCase: LoginUseCase,
    private val userRepository: UserRepository,
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.post<LoginRout, AuthBody> { _, body ->
            val response = loginUseCase(body)
            if (response == null) {
                call.respond(HttpStatusCode.Gone)
            } else {
                call.respond(response)
            }
        }

        routing.post<CreateAccountRout, AuthBody> { _, body ->
            if (userRepository.isNotExist(body.username)) {
                userRepository.create(body.username, body.password)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}
