package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class ChatId(val value: String)

@Resource(ApiRouts.CHATS)
data object ChatRout

@Serializable
data class CreateChatBody(val name: String)

@Resource(ApiRouts.CHATS)
data object CreateChatRout

@Serializable
data class Chat(
    val id: ChatId,
    val name: String
)
