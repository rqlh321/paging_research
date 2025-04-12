package ru.gubatenko.message.data

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.Response
import ru.gubatenko.common.UserId

@Resource(ApiRouts.MESSAGES)
data class MessagesRout(
    val chatId: ChatId,
    val messageId: MessageId? = null,
    val isDirectionToLatest: Boolean,
    val limit: Long
)

@Serializable
data class CreateMessageBody(
    val chatId: ChatId,
    val text: String
)

@Resource(ApiRouts.MESSAGES)
data object CreateMessageRout

@Serializable
data class Message(
    val id: MessageId,
    val senderId: UserId,
    val timestamp: Long,
    val text: String
) : Response()

@Serializable
data class Messages(
    val items: List<Message>
) : Response()