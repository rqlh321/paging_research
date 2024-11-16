package ru.gubatenko.chat.domain

abstract class UpdateChatUseCase : suspend (UpdateChatUseCase.Args) -> UpdateChatUseCase.Result {
    sealed class Args {
        data object Default : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object Fail : Result()
    }
}