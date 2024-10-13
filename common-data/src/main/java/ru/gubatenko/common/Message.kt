package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MessageId(private val value: String)

@Resource(ApiRouts.MESSAGES)
data class MessagesRout(val chatId: ChatId)

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
    val text: String
)
