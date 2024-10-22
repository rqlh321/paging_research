package ru.gubatenko.server.data

import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.common.Response

abstract class DataStore {

    abstract fun createChat(body: CreateChatBody): Response

    abstract fun chats(): Response

    abstract fun createMessage(body: CreateMessageBody): Message

    abstract fun messages(rout: MessagesRout): Response
}