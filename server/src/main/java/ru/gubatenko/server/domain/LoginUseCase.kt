package ru.gubatenko.server.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.Credentials
import ru.gubatenko.common.Response
import java.util.Date

class LoginUseCase : UseCase<AuthBody>() {
    override suspend fun run(args: AuthBody): Response {
        return Credentials(
            accessToken = getToken(args.username, System.currentTimeMillis() + 60_000),
            refreshToken = getToken(args.username, System.currentTimeMillis() + 300_000)
        )
    }

    private fun getToken(
        username: String,
        expires: Long
    ): String = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(claim, username)
        .withExpiresAt(Date(expires))
        .sign(Algorithm.HMAC256(jwtSecret))

    companion object {
        const val jwtAudience = "jwt-audience"
        const val jwtIssuer = "https://jwt-provider-domain/"
        const val jwtRealm = "ktor sample app"
        const val jwtSecret = "secret"
        const val claim = "username"
    }
}