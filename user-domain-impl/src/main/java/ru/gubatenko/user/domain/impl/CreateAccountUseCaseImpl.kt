package ru.gubatenko.user.domain.impl

import ru.gubatenko.auth.data.CreateAccountBody
import ru.gubatenko.common.BadRequestResponseStatus
import ru.gubatenko.user.domain.CreateAccountUseCase
import ru.gubatenko.user.remote.api.UserApi
import java.io.IOException
import java.net.ConnectException

class CreateAccountUseCaseImpl(
    private val api: UserApi
) : CreateAccountUseCase() {
    override suspend fun invoke(args: Args) = try {
        api.create(
            CreateAccountBody(
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