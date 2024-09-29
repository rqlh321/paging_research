package com.example.paging_reserch.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging_reserch.MainViewModel
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDao
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.database.RemoteKeyDao
import com.example.paging_reserch.database.RemoteKeyDatabaseEntity
import com.example.paging_reserch.network.MessagesServiceApi
import kotlinx.coroutines.delay
import kotlin.random.Random

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
        Log.d(this::class.simpleName, loadType.toString())
        if (Random.nextBoolean()) {
            error("Error when $loadType")
        } else when (loadType) {
            LoadType.REFRESH -> refresh()
            LoadType.PREPEND -> prepend()
            LoadType.APPEND -> append()
        }
    } catch (e: Exception) {
        MediatorResult.Error(e)
    }

    private suspend fun refresh(): MediatorResult {
        val nextKeyForChat = remoteKeyDao.nextKeyForChatById(chatId)

        val response = messageService.messages(
            borderPosition = nextKeyForChat,
            limit = MainViewModel.INIT_SIZE
        )

        val messageEntities = response.map {
            MessageDatabaseEntity(
                chatId = chatId,
                id = it.id,
                timestamp = System.currentTimeMillis(),
                isWatched = true
            )
        }
        val remoteKeyEntity = RemoteKeyDatabaseEntity(
            chatId = chatId,
            nextKey = messageEntities.lastOrNull()?.id
        )

        database.withTransaction {
            remoteKeyDao.update(remoteKeyEntity)
            messageDao.update(messageEntities)
        }

        return MediatorResult.Success(
            endOfPaginationReached = response.size < MainViewModel.INIT_SIZE
        )
    }

    private suspend fun prepend(): MediatorResult {
        delay(3000)
        return MediatorResult.Success(endOfPaginationReached = true)
    }

    private suspend fun append(): MediatorResult {
        delay(3000)
        return MediatorResult.Success(endOfPaginationReached = true)
    }

}