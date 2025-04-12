package ru.gubatenko.user.domain

import ru.gubatenko.common.Password
import ru.gubatenko.common.Username

abstract class CreateAccountUseCase :
    suspend (CreateAccountUseCase.Args) -> CreateAccountUseCase.Result {
    data class Args(val username: Username, val password: Password)

    sealed class Result {
        data object Success : Result()
        data object BadUsernameOrPasswordFail : Result()
        data object ConnectionFail : Result()
    }
}