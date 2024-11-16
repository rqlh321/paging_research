package ru.gubatenko.message.domain

abstract class UpdateMessagesUseCase : suspend (UpdateMessagesUseCase.Args) -> UpdateMessagesUseCase.Result {
    sealed class Args {
        data object Default : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object Fail : Result()
    }
}