package com.example.paging_reserch.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDao
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.database.RemoteKeyDao
import com.example.paging_reserch.database.RemoteKeyDatabaseEntity
import com.example.paging_reserch.screen.chat.ChatViewModel
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.MessageId
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server_api.MessageApi

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val chatId: String,
    private val database: AppDatabase,
    private val messageDao: MessageDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val api: MessageApi,
) : RemoteMediator<Int, MessageDatabaseEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageDatabaseEntity>
    ) = try {
        when (loadType) {
            LoadType.REFRESH -> refresh()
            LoadType.PREPEND -> prepend()
            LoadType.APPEND -> append()
        }
    } catch (e: Exception) {
        MediatorResult.Error(e)
    }

    private suspend fun refresh(): MediatorResult {
        val remoteKey = remoteKeyDao.remoteKey(chatId)
        if (remoteKey == null) {
            val response = api.messages(
                MessagesRout(
                    chatId = ChatId(chatId),
                    isDirectionToLatest = true,
                    limit = ChatViewModel.INIT_SIZE.toLong()
                )
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    id = it.timestamp.toString(),
                    timestamp = it.timestamp,
                )
            }
            val appendKey = messageEntities.lastOrNull()?.id
            val remoteKeyEntity = RemoteKeyDatabaseEntity(
                chatId = chatId,
                prependKey = null,
                appendKey = if (response.size < ChatViewModel.INIT_SIZE) null else appendKey
            )
            database.withTransaction {
                remoteKeyDao.update(remoteKeyEntity)
                messageDao.update(messageEntities)
            }
        }
        return MediatorResult.Success(endOfPaginationReached = false)
    }

    private suspend fun prepend(): MediatorResult {
        val endOfPaginationReached = remoteKeyDao.remoteKey(chatId)?.prependKey?.let {
            val response = api.messages(
                MessagesRout(
                    chatId = ChatId(chatId),
                    messageId = MessageId(it),
                    isDirectionToLatest = false,
                    limit = ChatViewModel.INIT_SIZE.toLong()
                )
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    id = it.timestamp.toString(),
                    timestamp = it.timestamp,
                )
            }
            val prependKey = messageEntities.lastOrNull()?.id
            database.withTransaction {
                remoteKeyDao.updatePrepend(chatId, prependKey)
                messageDao.update(messageEntities)
            }
            response.size < ChatViewModel.INIT_SIZE
        } ?: true


        return MediatorResult.Success(endOfPaginationReached)
    }

    private suspend fun append(): MediatorResult {
        val endOfPaginationReached = remoteKeyDao.remoteKey(chatId)?.appendKey?.let {
            val response = api.messages(
                MessagesRout(
                    chatId = ChatId(chatId),
                    messageId = MessageId(it),
                    isDirectionToLatest = true,
                    limit = ChatViewModel.INIT_SIZE.toLong()
                )
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    id = it.timestamp.toString(),
                    timestamp = it.timestamp,
                )
            }
            val appendKey = messageEntities.lastOrNull()?.id
            database.withTransaction {
                remoteKeyDao.updateAppend(chatId, appendKey)
                messageDao.update(messageEntities)
            }
            response.size < ChatViewModel.INIT_SIZE
        } ?: true


        return MediatorResult.Success(endOfPaginationReached)
    }

}