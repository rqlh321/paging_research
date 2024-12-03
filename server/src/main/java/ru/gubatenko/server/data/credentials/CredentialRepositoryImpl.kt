package ru.gubatenko.server.data.credentials

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.jetbrains.exposed.sql.and
import ru.gubatenko.auth.data.Credentials
import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.Password
import ru.gubatenko.common.RefreshToken
import ru.gubatenko.common.UserId
import ru.gubatenko.common.Username
import ru.gubatenko.server.Const.claimUserId
import ru.gubatenko.server.Const.jwtAudience
import ru.gubatenko.server.Const.jwtIssuer
import ru.gubatenko.server.Const.jwtSecret
import ru.gubatenko.server.data.DaoStore
import ru.gubatenko.server.data.suspendTransaction
import ru.gubatenko.server.data.user.UserTable
import ru.gubatenko.server.domain.repo.CredentialRepository
import java.util.Date
import java.util.UUID

class CredentialRepositoryImpl(
    private val daoStore: DaoStore
) : CredentialRepository {

    override suspend fun checkUserId(
        username: Username,
        password: Password
    ) = suspendTransaction{ daoStore.userDao()
        .find {
            (UserTable.username eq username.value) and
                    (UserTable.password eq password.value)
        }
        .firstOrNull()
        ?.id
        ?.let { UserId(it.value.toString()) }
    }

    override suspend fun isTokenExist(
        tokenId: String,
        userId: UserId
    ): Boolean = suspendTransaction {
        daoStore.credentialDao()
            .find {
                (CredentialsTable.id eq UUID.fromString(tokenId)) and
                        (CredentialsTable.userId eq userId.uuid())
            }.empty().not()
    }

    override suspend fun createNewCredentials(userId: UserId): Credentials = suspendTransaction {
        val accessCredential = daoStore.credentialDao().new {
            this.token = accessToken(id.value.toString(), userId).value
            this.userId = userId.uuid()
        }
        val refreshCredential = CredentialsDAO.new {
            this.token = refreshToken(id.value.toString(), userId).value
            this.userId = userId.uuid()
        }
        Credentials(
            accessToken = AccessToken(accessCredential.token),
            refreshToken = RefreshToken(refreshCredential.token)
        )
    }

    private fun refreshToken(jwtId: String, userId: UserId) = RefreshToken(
        getToken(jwtId, userId, System.currentTimeMillis() + 300_000)
    )

    private fun accessToken(jwtId: String, userId: UserId) = AccessToken(
        getToken(jwtId, userId, System.currentTimeMillis() + 60_000)
    )

    private fun getToken(
        jwtId: String,
        userId: UserId,
        expires: Long
    ) = JWT.create()
        .withJWTId(jwtId)
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(claimUserId, userId.value)
        .withExpiresAt(Date(expires))
        .sign(Algorithm.HMAC256(jwtSecret))
}