package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import java.util.UUID

@JvmInline
@Serializable
value class ChatId(val value: String){
    fun uuid(): UUID = UUID.fromString(value)
}

@Resource(ApiRouts.CHATS)
data object ChatRout

@Serializable
data class CreateChatBody(val name: String)

@Resource(ApiRouts.CHATS)
data object CreateChatRout

@Serializable
data class Chat(
    val id: ChatId,
    val ownerId: UserId,
    val name: String
) : Response()

@Serializable
data class Chats(
    val items: List<Chat>
) : Response()
