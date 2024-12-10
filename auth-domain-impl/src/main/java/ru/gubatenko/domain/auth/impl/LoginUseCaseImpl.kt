package ru.gubatenko.domain.auth.impl

import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.credential.store.TokenStore
import ru.gubatenko.domain.auth.LoginUseCase
import java.io.IOException
import java.net.ConnectException

class LoginUseCaseImpl(
    private val api: AuthApi,
    private val tokenStore: TokenStore,
) : LoginUseCase() {
    override suspend fun invoke(
        args: Args
    ) = when (args) {
        is Args.ByPassword -> handleLoginByPassword(args)
    }

    private suspend fun handleLoginByPassword(args: Args.ByPassword) = try {
//        val body = AuthBody(args.username, args.password)
//        val result = api.auth(body)
//        tokenStore.update(result.accessToken, result.refreshToken)
        Result.Success
    } catch (e: ConnectException) {
        Result.ConnectionFail
    } catch (e: IOException) {
        Result.ConnectionFail
    } catch (e: BadRequestResponseStatus) {
        Result.WrongCredentials
    }
}