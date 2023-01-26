package com.example.paging_reserch.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging_reserch.MainViewModel
import com.example.paging_reserch.database.*
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
    ): MediatorResult = when (loadType) {
        LoadType.REFRESH -> refresh()
        LoadType.PREPEND -> prepend()
        LoadType.APPEND -> append()
    }

    private suspend fun refresh(): MediatorResult {
        val result = try {
            val nextKeyForChat = remoteKeyDao.nextKeyForChatById(chatId)

            val response = messageService.messages(
                borderPosition = nextKeyForChat,
                limit = MainViewModel.INIT_SIZE
            )

            val messageEntities = response.map {
                MessageDatabaseEntity(
                    chatId = chatId,
                    position = it.id,
                    isWatched = it.isWatched
                )
            }
            val remoteKeyEntity = RemoteKeyDatabaseEntity(
                chatId = chatId,
                nextKey = messageEntities.lastOrNull()?.position
            )

            database.withTransaction {
                remoteKeyDao.update(remoteKeyEntity)
                messageDao.update(messageEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.size < MainViewModel.INIT_SIZE
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
        return result
    }

    private suspend fun prepend() = MediatorResult.Success(endOfPaginationReached = true)

    private suspend fun append(): MediatorResult {
        val result = try {
            val nextKeyForChat = remoteKeyDao.nextKeyForChatById(chatId)
            if (nextKeyForChat == null) {
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                val response = messageService.messages(
                    borderPosition = nextKeyForChat,
                    limit = MainViewModel.PAGE_SIZE
                )

                val messageEntities = response.map {
                    MessageDatabaseEntity(
                        chatId = chatId,
                        position = it.id,
                        isWatched = it.isWatched
                    )
                }
                val remoteKeyEntity = RemoteKeyDatabaseEntity(
                    chatId = chatId,
                    nextKey = messageEntities.lastOrNull()?.position
                )

                database.withTransaction {
                    messageDao.update(messageEntities)
                    remoteKeyDao.update(remoteKeyEntity)
                }

                MediatorResult.Success(
                    endOfPaginationReached = response.size < MainViewModel.PAGE_SIZE
                )
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
        return result
    }

}