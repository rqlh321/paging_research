package ru.gubatenko.domain.auth.impl

import ru.gubatenko.domain.auth.IsCreateAccountAvailableUseCase

class IsCreateAccountAvailableUseCaseImpl : IsCreateAccountAvailableUseCase() {
    override suspend fun invoke(args: Args): Result {
        return Result(!args.isLoginInProgress && args.username.value.isNotBlank() && args.password.value.isNotBlank())
    }
}