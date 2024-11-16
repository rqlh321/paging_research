package ru.gubatenko.server.data.chat

import ru.gubatenko.chat.data.Chat
import ru.gubatenko.chat.data.Chats
import ru.gubatenko.chat.data.CreateChatBody
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.UserId
import ru.gubatenko.server.data.DaoStore
import ru.gubatenko.server.data.suspendTransaction
import ru.gubatenko.server.domain.repo.ChatRepository

class ChatRepositoryImpl(
    private val daoStore: DaoStore
) : ChatRepository {

    override suspend fun createChat(
        userId: UserId,
        body: CreateChatBody
    ) = suspendTransaction {
        daoToModel(
            daoStore.chatDao().new {
                ownerId = userId.uuid()
                name = body.name
            }
        )
    }

    override suspend fun chats(
        userId: UserId
    ) = suspendTransaction {
        Chats(
            daoStore.chatDao().find { (ChatTable.ownerId eq userId.uuid()) }.map(::daoToModel)
        )
    }

    fun daoToModel(
        dao: ChatDAO
    ) = Chat(
        id = ChatId(dao.id.value.toString()),
        ownerId = UserId(dao.ownerId.toString()),
        name = dao.name,
    )
}