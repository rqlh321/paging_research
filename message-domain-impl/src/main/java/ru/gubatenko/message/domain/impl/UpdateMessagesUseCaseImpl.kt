package ru.gubatenko.message.domain.impl

import ru.gubatenko.message.domain.UpdateMessagesUseCase

class UpdateMessagesUseCaseImpl(
    private val api: MessageApi,
    private val dao: MessageDao,
) : UpdateMessagesUseCase() {
    override suspend fun invoke(args: Args) = when (args) {
        Args.Default -> handleDefaultArgs(args)
    }

    private suspend fun handleDefaultArgs(args: Args): Result {
        TODO("Not yet implemented")
    }
}