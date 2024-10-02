package com.example.paging_reserch.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging_reserch.screen.chat.ChatViewModel
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDao
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.database.RemoteKeyDao
import com.example.paging_reserch.database.RemoteKeyDatabaseEntity
import com.example.paging_reserch.network.MessagesServiceApi

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val chatId: String,
    private val database: AppDatabase,
    private val messageDao: MessageDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val messageService: MessagesServiceApi,
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
            val initialKey: Long = System.currentTimeMillis()
            val response = messageService.messages(
                borderPosition = initialKey,
                isDirectionToLatest = true,
                limit = ChatViewModel.INIT_SIZE
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    id = it.timestamp.toString(),
                    timestamp = it.timestamp,
                    isWatched = true
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
            val response = messageService.messages(
                borderPosition = it.toLong(),
                isDirectionToLatest = false,
                limit = ChatViewModel.INIT_SIZE
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    id = it.timestamp.toString(),
                    timestamp = it.timestamp,
                    isWatched = true
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
            val response = messageService.messages(
                borderPosition = it.toLong(),
                isDirectionToLatest = true,
                limit = ChatViewModel.INIT_SIZE
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    id = it.timestamp.toString(),
                    timestamp = it.timestamp,
                    isWatched = true
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