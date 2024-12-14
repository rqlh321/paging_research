package ru.gubatenko.domain.auth.impl

import ru.gubatenko.domain.auth.CreateAccountUseCase
import java.io.IOException
import java.net.ConnectException

class CreateAccountUseCaseImpl : CreateAccountUseCase() {
    override suspend fun invoke(args: Args) = try {
        Result.Success
    } catch (e: ConnectException) {
        Result.ConnectionFail
    } catch (e: IOException) {
        Result.ConnectionFail
    }
}