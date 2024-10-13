package ru.gubatenko.server.data

import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.MessagesRout
import java.util.UUID

class DataStoreHashMaps : DataStore() {

    private val chats = hashMapOf<ChatId, Chat>()
    private val messages = hashMapOf<ChatId, HashMap<MessageId, Message>>()

    override fun createChat(body: CreateChatBody): Chat {
        val id = ChatId(UUID.randomUUID().toString())
        val chat = Chat(id, body.name)
        chats[id] = chat
        return chat
    }

    override fun chats(): List<Chat> {
        return chats.values.toList()
    }

    override fun createMessage(body: CreateMessageBody): Message {
        val chatId = body.chatId
        val text = body.text

        if (chats.containsKey(chatId)) {
            val messageId = MessageId(UUID.randomUUID().toString())
            val timestamp = System.currentTimeMillis()
            val message = Message(
                id = messageId,
                timestamp = timestamp,
                text = text
            )
            if (messages.containsKey(chatId)) {
                messages[chatId]?.put(messageId, message)
            } else {
                messages[chatId] = hashMapOf(messageId to message)
            }
            return message
        } else {
            error("chat is missing")
        }
    }

    override fun messages(rout: MessagesRout): List<Message> {
        return messages[rout.chatId]?.values.orEmpty().toList()
    }
}