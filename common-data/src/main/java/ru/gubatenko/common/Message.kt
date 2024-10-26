package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import java.util.UUID

@JvmInline
@Serializable
value class MessageId(val value: String) {
    fun uuid(): UUID = UUID.fromString(value)
}

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