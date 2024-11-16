package ru.gubatenko.chat.domain

abstract class CreateChatUseCase : suspend (CreateChatUseCase.Args) -> CreateChatUseCase.Result {
    sealed class Args {
        data class Default(val name: String) : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object Fail : Result()
    }
}