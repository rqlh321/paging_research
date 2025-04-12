package ru.gubatenko.user.domain

abstract class DeleteAccountUseCase : suspend (DeleteAccountUseCase.Args) -> DeleteAccountUseCase.Result {
    sealed class Args {
        data object Empty : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object ConnectionFail : Result()
    }
}