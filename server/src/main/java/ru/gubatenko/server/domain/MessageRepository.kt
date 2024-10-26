package ru.gubatenko.server.domain

import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.Messages
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.common.UserId

interface MessageRepository {

    suspend fun createMessage(userId: UserId, body: CreateMessageBody): Message

    suspend fun messages(userId: UserId, rout: MessagesRout): Messages
}