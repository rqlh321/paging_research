package ru.gubatenko.domain.auth.impl

import ru.gubatenko.domain.auth.LogoutUseCase

class LogoutUseCaseImpl(
    private val authApi: AuthApi
) : LogoutUseCase() {
    override suspend fun invoke(args: Args) = when (args) {
        Args.Empty -> handleLogout()
    }

    private suspend fun handleLogout(): Result {
        authApi.logout()
        return Result.Success
    }
}