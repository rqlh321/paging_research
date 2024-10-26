package ru.gubatenko.server.data

import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.Messages
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.common.UserId
import ru.gubatenko.server.domain.MessageRepository

class MessageRepositoryImpl : MessageRepository {
    override suspend fun createMessage(
        userId: UserId,
        body: CreateMessageBody
    ) = suspendTransaction {
        daoToModel(
            MessageDAO.new {
                senderId = userId.uuid()
                text = body.text
                timestamp = System.currentTimeMillis()
            }
        )
    }

    override suspend fun messages(
        userId: UserId,
        rout: MessagesRout
    ) = suspendTransaction {
        Messages(
            MessageDAO
                .find { (MessageTable.chatId eq rout.chatId.uuid()) }
                .limit(rout.limit.toInt())
                .map(::daoToModel)
        )
    }

    private fun daoToModel(
        dao: MessageDAO
    ) = Message(
        id = MessageId(dao.id.value.toString()),
        senderId = UserId(dao.senderId.toString()),
        text = dao.text,
        timestamp = dao.timestamp,
    )

}