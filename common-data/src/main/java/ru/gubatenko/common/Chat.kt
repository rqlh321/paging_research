package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class ChatId(private val value: String)

@Resource(ApiRouts.CHATS)
data object ChatRout

@Resource(ApiRouts.CHATS)
data object CreateChatRout

@Serializable
data class Chat(
    val id: ChatId,
    val name: String
)
