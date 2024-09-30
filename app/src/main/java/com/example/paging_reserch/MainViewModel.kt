package com.example.paging_reserch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.room.Room
import com.example.paging_reserch.adapter.MessageItem
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.network.MessagesServiceApiMock
import com.example.paging_reserch.paging.MessageRemoteMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val db = Room.databaseBuilder(app, AppDatabase::class.java, "database-name")
        .build()
    private val messagesService = MessagesServiceApiMock()
    private val messageDao = db.messageDao()
    private val remoteKeyDao = db.remoteKeyDao()

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INIT_SIZE,
            maxSize = MAXIMUM_MESSAGES_IN_LIST,
            prefetchDistance = PREFETCH_SIZE,
        ),
        remoteMediator = MessageRemoteMediator(
            chatId = CHAT_ID,
            database = db,
            messageDao = messageDao,
            messageService = messagesService,
            remoteKeyDao = remoteKeyDao,
        ),
    ) { messageDao.read() }

    private val positionToScrollMutable = MutableStateFlow(0)
    val positionToScroll: StateFlow<Int> = positionToScrollMutable

    val pagingDataFlow: Flow<PagingData<MessageItem>> = pager.flow
        .map { page ->
            page.map(::MessageItem)
        }
        .cachedIn(viewModelScope)

    init {
        messageDao.notWatchedMessagesFlow()
            .onEach {
                delay(100)
                val position = it.size
                positionToScrollMutable.emit(position)
            }
            .launchIn(viewModelScope)
    }

    fun emulateMessageReceive() {
        viewModelScope.launch {
            val id = messageDao.earliestPosition().toLong().plus(1).toString()
            val incomeMessages = listOf(
                MessageDatabaseEntity(
                    id = id,
                    chatId = CHAT_ID,
                    timestamp = System.currentTimeMillis(),
                    isWatched = false
                )
            )
            messageDao.update(incomeMessages)
        }
    }

    fun allMessagesWatched() {
        viewModelScope.launch {
            messageDao.markAsWatched()
        }
    }

    fun messageWatched(message: MessageItem) {
        viewModelScope.launch {
            delay(500)
            messageDao.watched(message.id)
        }
    }

    companion object {
        const val PREFETCH_SIZE = 30
        const val INIT_SIZE = 30
        const val PAGE_SIZE = 10
        const val MAXIMUM_MESSAGES_IN_LIST = PAGE_SIZE + (INIT_SIZE * 2)

        const val CHAT_ID = "123"
    }
}