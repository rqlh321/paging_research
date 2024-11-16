package ru.gubatenko.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.gubatenko.server.domain.usecase.TokenValidateUseCase
import kotlin.time.Duration.Companion.milliseconds

fun Application.configureRouting() {
    val validationUseCase by inject<TokenValidateUseCase>()
    authentication {
        jwt {
            realm = Const.jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(Const.jwtSecret))
                    .withAudience(Const.jwtAudience)
                    .withIssuer(Const.jwtIssuer)
                    .build()
            )
            validate { validationUseCase(it) }
        }
    }
    install(Koin) {
        slf4jLogger()
        modules(dataModule)
        modules(useCaseModule)
        modules(routingModule)
    }
    install(Resources)
    install(ContentNegotiation) { json() }
    install(WebSockets) {
        pingPeriod = 15000.milliseconds
        timeout = 15000.milliseconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }

    val routingSetup by inject<List<RoutingSetup>>()

    routing {
        routingSetup.forEach { it.setupRouting(this) }
    }
}