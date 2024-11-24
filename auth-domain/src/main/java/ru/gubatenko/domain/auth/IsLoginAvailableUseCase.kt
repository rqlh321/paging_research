package ru.gubatenko.domain.auth

import ru.gubatenko.common.Password
import ru.gubatenko.common.Username

abstract class IsLoginAvailableUseCase :
    suspend (IsLoginAvailableUseCase.Args) -> IsLoginAvailableUseCase.Result {
    data class Args(
        val isLoginInProgress: Boolean,
        val username: Username,
        val password: Password
    )

    data class Result(val isValid: Boolean)
}