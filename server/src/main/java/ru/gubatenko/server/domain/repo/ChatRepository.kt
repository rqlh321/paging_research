package ru.gubatenko.server.domain.repo

import ru.gubatenko.common.Chat
import ru.gubatenko.common.Chats
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.UserId

interface ChatRepository {

    suspend fun createChat(userId: UserId, body: CreateChatBody): Chat

    suspend fun chats(userId: UserId): Chats
}