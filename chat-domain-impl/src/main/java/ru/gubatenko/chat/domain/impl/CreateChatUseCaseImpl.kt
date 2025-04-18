package ru.gubatenko.chat.domain.impl

import ru.gubatenko.chat.data.CreateChatBody
import ru.gubatenko.chat.domain.CreateChatUseCase
import ru.gubatenko.chat.local.data.ChatDao
import ru.gubatenko.chat.local.data.ChatDatabaseEntity

class CreateChatUseCaseImpl(
    private val dao: ChatDao,
    private val api: ChatApiImpl
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