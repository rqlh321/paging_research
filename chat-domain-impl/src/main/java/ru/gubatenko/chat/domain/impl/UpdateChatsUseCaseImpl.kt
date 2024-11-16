package ru.gubatenko.chat.domain.impl

import ru.gubatenko.chat.domain.UpdateChatUseCase

class UpdateChatsUseCaseImpl(
    private val dao: ChatDao,
    private val api: ChatApi
) : UpdateChatUseCase() {
    override suspend fun invoke(args: Args) = when (args) {
        Args.Default -> handleDefault(args)
    }

    private suspend fun handleDefault(args: Args): Result {
        val chats = api.chats()
        val entities = chats.items.map { ChatDatabaseEntity(id = it.id.value) }
        dao.upsert(entities)
        return Result.Success
    }
}