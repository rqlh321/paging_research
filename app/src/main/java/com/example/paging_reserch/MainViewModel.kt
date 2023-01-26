package com.example.paging_reserch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.room.Room
import com.example.paging_reserch.adapter.Message
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.network.MessagesServiceApiMock
import com.example.paging_reserch.paging.MessageRemoteMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    val pagingDataFlow: StateFlow<PagingData<Message>> = pager.flow
        .map { page -> page.map { Message(it.position, it.position.toString(), it.isWatched) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        messageDao.notWatchedMessagesFlow()
            .onEach {
                delay(100)
                positionToScrollMutable.emit(it.size)
            }
            .launchIn(viewModelScope)
    }

    fun emulateMessageReceive() {
        viewModelScope.launch {
            val position = messageDao.earliestPosition().minus(1)
            val incomeMessages = listOf(
                MessageDatabaseEntity(
                    position = position,
                    chatId = CHAT_ID,
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

    fun messageWatched(message: Message) {
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