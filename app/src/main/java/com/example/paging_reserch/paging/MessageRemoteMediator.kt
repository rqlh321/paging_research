package com.example.paging_reserch.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.gubatenko.common.database.MessageDatabaseEntity
import ru.gubatenko.common.repo.MessageRepo

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val chatId: String,
    private val repo: MessageRepo,
) : RemoteMediator<Int, MessageDatabaseEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageDatabaseEntity>
    ) = try {
        MediatorResult.Success(
            when (loadType) {
                LoadType.REFRESH -> repo.refresh(chatId)
                LoadType.PREPEND -> repo.prepend(chatId)
                LoadType.APPEND -> repo.append(chatId)
            }
        )
    } catch (e: Exception) {
        MediatorResult.Error(e)
    }

}