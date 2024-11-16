package ru.gubatenko.chat.domain.impl

import ru.gubatenko.chat.data.CreateChatBody
import ru.gubatenko.chat.domain.CreateChatUseCase

class CreateChatUseCaseImpl(
    private val dao: ChatDao,
    private val api: ChatApi
) : CreateChatUseCase() {
    override suspend fun invoke(args: Args) = when (args) {
        is Args.Default -> handleDefault(args)
    }

    private suspend fun handleDefault(args: Args.Default): Result {
        val chatBody = CreateChatBody(args.name)
        val chat = api.create(chatBody)
        val entity = ChatDatabaseEntity(id = chat.id.value)
        dao.upsert(entity)
        return Result.Success
    }
}