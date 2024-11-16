package ru.gubatenko.server.domain.repo

import ru.gubatenko.common.UserId
import ru.gubatenko.message.data.CreateMessageBody
import ru.gubatenko.message.data.Message
import ru.gubatenko.message.data.Messages
import ru.gubatenko.message.data.MessagesRout

interface MessageRepository {

    suspend fun createMessage(userId: UserId, body: CreateMessageBody): Message

    suspend fun messages(userId: UserId, rout: MessagesRout): Messages
}