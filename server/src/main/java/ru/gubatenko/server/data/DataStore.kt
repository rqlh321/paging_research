package ru.gubatenko.server.data

import ru.gubatenko.common.Chat
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.Response
import java.util.UUID

class DataStore {

    private val chats = hashMapOf<ChatId, Chat>()
    private val messages = hashMapOf<ChatId, HashMap<MessageId, Message>>()

    fun createChat(body: CreateChatBody): Response<ChatId> {
        val id = ChatId(UUID.randomUUID().toString())
        chats[id] = Chat(id, body.name)
        return Response(id)
    }

    fun chats(): Response<List<Chat>> {
        return Response(chats.values.toList())
    }

    fun createMessage(body: CreateMessageBody): Response<MessageId> {
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
            return Response(messageId)
        } else {
            error("chat is missing")
        }
    }

    fun messages(chatId: ChatId): Result<List<Message>> {
        return Result.success(messages[chatId]?.values.orEmpty().toList())
    }
}