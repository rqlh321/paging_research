package com.example.paging_reserch.screen.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import androidx.room.Room
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.network.MessagesServiceApiMock
import com.example.paging_reserch.paging.MessageRemoteMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatViewModel(app: Application) : AndroidViewModel(app) {

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

    private val dividerStateFlow = MutableStateFlow("")
    val pagingDataFlow: Flow<PagingData<MessageItem>> = combine(
        pager.flow
            .map { it.map(::TextMessageItem) }
            .map {
                it.insertSeparators { m1: TextMessageItem?, m2: TextMessageItem? ->
                    val newerDate = m1?.date
                    val olderDate = m2?.date
                    if (newerDate != null && olderDate != null && newerDate != olderDate) {
                        DateMessageItem(date = newerDate)
                    } else {
                        null
                    }
                }
            }
            .cachedIn(viewModelScope),
        dividerStateFlow
    ) { pager, id ->
        pager.insertSeparators { messageItem: MessageItem?, _: MessageItem? ->
            if (messageItem?.id == id) {
                DividerMessageItem(text = "- divider -")
            } else {
                null
            }
        }
    }.cachedIn(viewModelScope)

    fun emulateMessageReceive() {
        viewModelScope.launch {
            val currentTimeMillis = System.currentTimeMillis()
            val incomeMessages = listOf(
                MessageDatabaseEntity(
                    id = currentTimeMillis.toString(),
                    chatId = CHAT_ID,
                    timestamp = currentTimeMillis,
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

    fun onMessageClick(message: MessageItem) {
        viewModelScope.launch {
            dividerStateFlow.emit(message.id)
        }
    }

    fun messageWatched(message: TextMessageItem) {
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