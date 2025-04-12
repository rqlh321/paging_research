package ru.gubatenko.user.domain.impl

import ru.gubatenko.user.domain.DeleteAccountUseCase

class DeleteAccountUseCaseImpl(
    private val userApi: UserApi
) : DeleteAccountUseCase() {
    override suspend fun invoke(args: Args) = when (args) {
        Args.Empty -> handleLogout()
    }

    private suspend fun handleLogout(): Result {
        userApi.delete()
        return Result.Success
    }
}