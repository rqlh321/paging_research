package ru.gubatenko.server.data

import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.Chats
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.Messages
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.common.UserId
import java.util.UUID

class DataStoreHashMaps : DataStore() {

    private val chats = hashMapOf<ChatId, Chat>()
    private val messages = hashMapOf<ChatId, HashMap<MessageId, Message>>()

    override fun createChat(body: CreateChatBody): Chat {
        val chat = Chat(ChatId(UUID.randomUUID().toString()),UserId("test"), body.name)
        chats[chat.id] = chat
        return chat
    }

    override fun chats(): Chats {
        return Chats(chats.values.toList())
    }

    override fun createMessage(body: CreateMessageBody): Message {
        val chatId = body.chatId
        val text = body.text

        if (chats.containsKey(chatId)) {
            val messageId = MessageId(UUID.randomUUID().toString())
            val timestamp = System.currentTimeMillis()
            val message = Message(
                id = messageId,
                senderId = UserId("test"),
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

    override fun messages(rout: MessagesRout): Messages {
        val allMessages = messages[rout.chatId]?.values.orEmpty().toList()
            .sortedBy { it.timestamp }
            .let { if (rout.isDirectionToLatest) it else it.reversed() }
        val index = allMessages.indexOfFirst { it.id == rout.messageId }
        val range = if (index == -1) {
            val endInclusive = rout.limit.toInt() - 1

            IntRange(
                0,
                if (endInclusive > allMessages.lastIndex) allMessages.lastIndex else endInclusive
            )
        } else {
            val endInclusive = index + rout.limit.toInt()

            IntRange(
                index + 1,
                if (endInclusive > allMessages.lastIndex) allMessages.lastIndex else endInclusive
            )
        }
        return Messages(allMessages.slice(range))
    }
}