package ru.gubatenko.server.data

import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessagesRout

abstract class DataStore {

    abstract fun createChat(body: CreateChatBody): Chat

    abstract fun chats(): List<Chat>

    abstract fun createMessage(body: CreateMessageBody): Message

    abstract fun messages(rout: MessagesRout): List<Message>
}