package ru.gubatenko.server.domain.usecase

import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.Response
import ru.gubatenko.server.domain.UseCase
import ru.gubatenko.server.domain.repo.CredentialRepository

class LoginUseCase(
    private val repo: CredentialRepository
) : UseCase<AuthBody>() {
    override suspend fun run(args: AuthBody): Response {
        val userId = repo.checkUserId(args.username, args.password)
            ?: error("Wrong login or password")
        return repo.createNewCredentials(userId)
    }
}