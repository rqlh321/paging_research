package ru.gubatenko.server.data

import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessageId
import java.util.UUID

class DataStore {

    private val chats = hashMapOf<ChatId, Chat>()
    private val messages = hashMapOf<ChatId, HashMap<MessageId, Message>>()

    fun createChat(body: CreateChatBody): ChatId {
        val id = ChatId(UUID.randomUUID().toString())
        chats[id] = Chat(id, body.name)
        return id
    }

    fun chats(): List<Chat> {
        return chats.values.toList()
    }

    fun createMessage(body: CreateMessageBody): MessageId {
        val chatId = body.chatId
        val text = body.text

        if (chats.containsKey(chatId)) {
            val messageId = MessageId(UUID.randomUUID().toString())
            val message = Message(
                id = messageId,
                text = text
            )
            if (messages.containsKey(chatId)) {
                messages[chatId]?.put(messageId, message)
            } else {
                messages[chatId] = hashMapOf(messageId to message)
            }
            return messageId
        } else {
            error("chat is missing")
        }
    }

    fun messages(chatId: ChatId): List<Message> {
        return messages[chatId]?.values.orEmpty().toList()
    }
}