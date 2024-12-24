package ru.gubatenko.domain.auth.impl

import ru.gubatenko.auth.data.AuthBody
import ru.gubatenko.common.BadRequestResponseStatus
import ru.gubatenko.domain.auth.CreateAccountUseCase
import java.io.IOException
import java.net.ConnectException

class CreateAccountUseCaseImpl(
    private val api: AuthApi
) : CreateAccountUseCase() {
    override suspend fun invoke(args: Args) = try {
        api.create(
            AuthBody(
                username = args.username,
                password = args.password
            )
        )
        Result.Success
    } catch (e: ConnectException) {
        Result.ConnectionFail
    } catch (e: IOException) {
        Result.ConnectionFail
    } catch (e: BadRequestResponseStatus) {
        Result.BadUsernameOrPasswordFail
    }
}