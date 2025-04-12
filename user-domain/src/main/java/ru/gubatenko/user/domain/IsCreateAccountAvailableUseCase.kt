package ru.gubatenko.user.domain

import ru.gubatenko.common.Password
import ru.gubatenko.common.Username

abstract class IsCreateAccountAvailableUseCase :
    suspend (IsCreateAccountAvailableUseCase.Args) -> IsCreateAccountAvailableUseCase.Result {
    data class Args(
        val isLoginInProgress: Boolean,
        val username: Username,
        val password: Password
    )

    data class Result(val isValid: Boolean)
}