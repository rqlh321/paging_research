package ru.gubatenko.server.domain.usecase

import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import ru.gubatenko.common.UserId
import ru.gubatenko.server.Const
import ru.gubatenko.server.domain.UseCase
import ru.gubatenko.server.domain.repo.CredentialRepository

class TokenValidateUseCase(
    val repo: CredentialRepository
) : UseCase<JWTCredential, JWTPrincipal?>() {
    override suspend fun invoke(args: JWTCredential): JWTPrincipal? {
        return if (args.payload.audience.contains(Const.jwtAudience)) {
            val tokenId = args.jwtId ?: return null
            val userId = UserId(args.issuer ?: return null)
            if (repo.isTokenExist(tokenId, userId)) {
                JWTPrincipal(args.payload)
            } else {
                null
            }
        } else {
            null
        }
    }
}