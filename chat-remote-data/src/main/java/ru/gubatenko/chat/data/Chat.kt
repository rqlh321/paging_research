package ru.gubatenko.chat.data

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.Response
import ru.gubatenko.common.UserId


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
