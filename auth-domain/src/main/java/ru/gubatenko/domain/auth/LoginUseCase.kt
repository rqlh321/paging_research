package ru.gubatenko.domain.auth

import ru.gubatenko.common.Password
import ru.gubatenko.common.Username

abstract class LoginUseCase : suspend (LoginUseCase.Args) -> LoginUseCase.Result {
    sealed class Args {
        data class ByPassword(val username: Username, val password: Password) : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object Fail : Result()
    }
}