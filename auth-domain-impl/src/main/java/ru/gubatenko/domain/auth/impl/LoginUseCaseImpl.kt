package ru.gubatenko.domain.auth.impl

import kotlinx.coroutines.CancellationException
import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.credential.store.CredentialSaveUseCase
import ru.gubatenko.domain.auth.LoginUseCase

class LoginUseCaseImpl(
    private val api: AuthApi,
    private val credentialSaveUseCase: CredentialSaveUseCase,
) : LoginUseCase() {
    override suspend fun invoke(
        args: Args
    ) = when (args) {
        is Args.ByPassword -> handleLoginByPassword(args)
    }

    private suspend fun handleLoginByPassword(args: Args.ByPassword) = try {
        val body = AuthBody(args.username, args.password)
        val result = api.auth(body)
        credentialSaveUseCase(result.accessToken, result.refreshToken)
        Result.Success
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (e: Exception) {
        Result.Fail
    }
}