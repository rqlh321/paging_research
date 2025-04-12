package ru.gubatenko.server.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import ru.gubatenko.auth.data.CreateAccountBody
import ru.gubatenko.auth.data.CreateAccountRout
import ru.gubatenko.auth.data.DeleteUserBody
import ru.gubatenko.auth.data.DeleteUserRout
import ru.gubatenko.server.RoutingSetup
import ru.gubatenko.server.domain.repo.UserRepository

class UserRouting(
    private val userRepository: UserRepository,
) : RoutingSetup() {

    override fun setupRouting(routing: Routing) {
        routing.post<CreateAccountRout, CreateAccountBody> { _, body ->
            if (userRepository.isNotExist(body.username)) {
                userRepository.create(body.username, body.password)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
        routing.authenticate {
            post<DeleteUserRout, DeleteUserBody> { _, _ ->
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
