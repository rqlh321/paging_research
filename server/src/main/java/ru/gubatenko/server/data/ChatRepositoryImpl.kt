package ru.gubatenko.server.data

import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.Chats
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.UserId
import ru.gubatenko.server.domain.ChatRepository

class ChatRepositoryImpl : ChatRepository {
    override suspend fun createChat(
        userId: UserId,
        body: CreateChatBody
    ) = suspendTransaction {
        daoToModel(
            ChatDAO.new {
                ownerId = userId.uuid()
                name = body.name
            }
        )
    }

    override suspend fun chats(
        userId: UserId
    ) = suspendTransaction {
        Chats(
            ChatDAO.find { (ChatTable.ownerId eq userId.uuid()) }.map(::daoToModel)
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