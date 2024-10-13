package ru.gubatenko.common.repo

import ru.gubatenko.common.ChatId
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.common.database.MessageDao
import ru.gubatenko.common.database.MessageDatabaseEntity
import ru.gubatenko.common.database.RemoteKeyDao
import ru.gubatenko.common.database.RemoteKeyDatabaseEntity
import ru.gubatenko.server_api.MessageApi

class MessageRepo(
    private val remoteKeyDao: RemoteKeyDao,
    private val messageDao: MessageDao,
) {
    private val api: MessageApi = MessageApi()

    suspend fun refresh(chatId: String): Boolean {
        val remoteKey = remoteKeyDao.remoteKey(chatId)
        if (remoteKey == null) {
            val response = api.messages(
                MessagesRout(
                    chatId = ChatId(chatId),
                    isDirectionToLatest = true,
                    limit = INIT_SIZE.toLong()
                )
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    messageId = it.id.value,
                    timestamp = it.timestamp,
                )
            }
            val appendKey = messageEntities.lastOrNull()?.messageId
            val remoteKeyEntity = RemoteKeyDatabaseEntity(
                chatId = chatId,
                prependKey = null,
                appendKey = if (response.size < INIT_SIZE) null else appendKey
            )
            remoteKeyDao.update(remoteKeyEntity)
            messageDao.update(messageEntities)
        }
        return true
    }

    suspend fun prepend(chatId: String): Boolean {
        val endOfPaginationReached = remoteKeyDao.remoteKey(chatId)?.prependKey?.let {
            val response = api.messages(
                MessagesRout(
                    chatId = ChatId(chatId),
                    messageId = MessageId(it),
                    isDirectionToLatest = false,
                    limit = INIT_SIZE.toLong()
                )
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    messageId = it.id.value,
                    timestamp = it.timestamp,
                )
            }
            val prependKey = messageEntities.lastOrNull()?.messageId
            remoteKeyDao.updatePrepend(chatId, prependKey)
            messageDao.update(messageEntities)
            response.size < INIT_SIZE
        } ?: true
        return endOfPaginationReached
    }

    suspend fun append(chatId: String): Boolean {
        val endOfPaginationReached = remoteKeyDao.remoteKey(chatId)?.appendKey?.let {
            val response = api.messages(
                MessagesRout(
                    chatId = ChatId(chatId),
                    messageId = MessageId(it),
                    isDirectionToLatest = true,
                    limit = INIT_SIZE.toLong()
                )
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    messageId = it.id.value,
                    timestamp = it.timestamp,
                )
            }
            val appendKey = messageEntities.lastOrNull()?.messageId
            remoteKeyDao.updateAppend(chatId, appendKey)
            messageDao.update(messageEntities)
            response.size < INIT_SIZE
        } ?: true
        return endOfPaginationReached
    }

    companion object {
        const val INIT_SIZE = 30
    }
}