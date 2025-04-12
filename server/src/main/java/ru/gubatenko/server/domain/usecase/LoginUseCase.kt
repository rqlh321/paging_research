package ru.gubatenko.server.domain.usecase

import ru.gubatenko.auth.data.LoginBody
import ru.gubatenko.common.Response
import ru.gubatenko.server.domain.UseCase
import ru.gubatenko.server.domain.repo.CredentialRepository

class LoginUseCase(
    private val repo: CredentialRepository
) : UseCase<LoginBody, Response?>() {
    override suspend fun invoke(
        args: LoginBody
    ) = repo.checkUserId(args.username, args.password)?.let {
            repo.createNewCredentials(it)
        }
}