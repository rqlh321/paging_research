package ru.gubatenko.server.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.Credentials
import ru.gubatenko.common.Response
import java.util.Date

class LoginUseCase : UseCase<AuthBody>() {
    override suspend fun run(args: AuthBody): Response {
        val userId = if (args.username == "test") {
            TEST_USER_ID
        } else {
            error("")
        }
        return Credentials(
            accessToken = getToken(userId, System.currentTimeMillis() + 60_000),
            refreshToken = getToken(userId, System.currentTimeMillis() + 300_000)
        )
    }

    private fun getToken(
        userId: String,
        expires: Long
    ): String = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(claimUserId, userId)
        .withExpiresAt(Date(expires))
        .sign(Algorithm.HMAC256(jwtSecret))

    companion object {
        const val TEST_USER_ID = "8D8AC610-566D-4EF0-9C22-186B2A5ED793"

        const val jwtAudience = "jwt-audience"
        const val jwtIssuer = "https://jwt-provider-domain/"
        const val jwtRealm = "ktor sample app"
        const val jwtSecret = "secret"
        const val claimUserId = "user_id"
    }
}