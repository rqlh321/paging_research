package ru.gubatenko.server.domain.usecase

import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.common.Response
import ru.gubatenko.server.domain.UseCase
import ru.gubatenko.server.domain.repo.CredentialRepository

class LoginUseCase(
    private val repo: CredentialRepository
) : UseCase<AuthBody, Response>() {
    override suspend fun invoke(args: AuthBody): Response {
        val userId = repo.checkUserId(args.username, args.password)
            ?: error("Wrong login or password")
        return repo.createNewCredentials(userId)
    }
}