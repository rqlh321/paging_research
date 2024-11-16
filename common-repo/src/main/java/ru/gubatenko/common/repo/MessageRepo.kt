package ru.gubatenko.common.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.common.database.MessageDao
import ru.gubatenko.common.database.MessageDatabaseEntity
import ru.gubatenko.server_api.MessageApi

class MessageRepo(
    private val messageDao: MessageDao,
) {
    private val api: MessageApi = MessageApi()

    suspend fun refresh(chatId: String): Boolean = withContext(Dispatchers.IO) {
        val response = api.messages(
            MessagesRout(
                chatId = ChatId(chatId),
                isDirectionToLatest = true,
                limit = INIT_SIZE.toLong()
            )
        )

        val messageEntities = response.items.map {
            MessageDatabaseEntity(
                chatId = chatId,
                messageId = it.id.value,
                timestamp = it.timestamp,
            )
        }
        val appendKey = messageEntities.lastOrNull()?.messageId

        messageDao.update(messageEntities)
        true
    }

    suspend fun prepend(chatId: String): Boolean = withContext(Dispatchers.IO) {
        val response = api.messages(
            MessagesRout(
                chatId = ChatId(chatId),
                messageId = MessageId(""),
                isDirectionToLatest = false,
                limit = INIT_SIZE.toLong()
            )
        )

        val messageEntities = response.items.map {
            MessageDatabaseEntity(
                chatId = chatId,
                messageId = it.id.value,
                timestamp = it.timestamp,
            )
        }
        messageDao.update(messageEntities)
        response.items.size < INIT_SIZE
    }

    suspend fun append(chatId: String): Boolean = withContext(Dispatchers.IO) {
        val response = api.messages(
            MessagesRout(
                chatId = ChatId(chatId),
                messageId = MessageId(""),
                isDirectionToLatest = true,
                limit = INIT_SIZE.toLong()
            )
        )

        val messageEntities = response.items.map {
            MessageDatabaseEntity(
                chatId = chatId,
                messageId = it.id.value,
                timestamp = it.timestamp,
            )
        }
        messageDao.update(messageEntities)
        response.items.size < INIT_SIZE
    }

    companion object {
        const val INIT_SIZE = 30
    }
}