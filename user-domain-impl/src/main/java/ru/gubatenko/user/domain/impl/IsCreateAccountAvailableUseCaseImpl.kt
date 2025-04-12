package ru.gubatenko.user.domain.impl

import ru.gubatenko.user.domain.IsCreateAccountAvailableUseCase

class IsCreateAccountAvailableUseCaseImpl : IsCreateAccountAvailableUseCase() {
    override suspend fun invoke(args: Args): Result {
        return Result(!args.isLoginInProgress && args.username.value.isNotBlank() && args.password.value.isNotBlank())
    }
}