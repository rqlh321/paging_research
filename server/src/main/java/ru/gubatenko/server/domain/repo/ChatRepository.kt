package ru.gubatenko.server.domain.repo

import ru.gubatenko.chat.data.Chat
import ru.gubatenko.chat.data.Chats
import ru.gubatenko.chat.data.CreateChatBody
import ru.gubatenko.common.UserId

interface ChatRepository {

    suspend fun createChat(userId: UserId, body: CreateChatBody): Chat

    suspend fun chats(userId: UserId): Chats
}