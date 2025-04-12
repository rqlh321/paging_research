package ru.gubatenko.domain.auth

abstract class LogoutUseCase : suspend (LogoutUseCase.Args) -> LogoutUseCase.Result {
    sealed class Args {
        data object Empty : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object ConnectionFail : Result()
    }
}