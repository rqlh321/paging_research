package com.example.paging_reserch.screen.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
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
import com.example.paging_reserch.network.MessagesServiceApiMock
import com.example.paging_reserch.paging.MessageRemoteMediator
import com.example.paging_reserch.screen.preset.ChatPresetViewModel.Companion.CHAT_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val chatId = savedStateHandle.get<String>(CHAT_KEY)

    private val db = Room.databaseBuilder(app, AppDatabase::class.java, "database-name").build()
    private val messagesService = MessagesServiceApiMock()
    private val chatDao = db.chatDao()
    private val messageDao = db.messageDao()
    private val remoteKeyDao = db.remoteKeyDao()
    private val clickedMessageIdFlow = MutableStateFlow("")

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
        clickedMessageIdFlow,
        chatDao.firstUnreadMessageIdFlow(CHAT_ID).distinctUntilChanged()
    ) { pager, clickedMessageId, firstUnreadMessageId ->
        pager.insertSeparators { messageItem: MessageItem?, _: MessageItem? ->
            if (messageItem?.id == firstUnreadMessageId) {
                DividerMessageItem(text = "- New -")
            } else {
                null
            }
        }
            .insertSeparators { messageItem: MessageItem?, _: MessageItem? ->
                if (messageItem?.id == clickedMessageId) {
                    DividerMessageItem(text = "- divider -")
                } else {
                    null
                }
            }
    }.cachedIn(viewModelScope)

    fun onMessageClick(message: MessageItem) {
        viewModelScope.launch {
            clickedMessageIdFlow.emit(message.id)
        }
    }

    companion object {
        const val CHAT_KEY = "chat_key"

        const val PREFETCH_SIZE = 30
        const val INIT_SIZE = 30
        const val PAGE_SIZE = 10
        const val MAXIMUM_MESSAGES_IN_LIST = PAGE_SIZE + (INIT_SIZE * 2)
    }
}