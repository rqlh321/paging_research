package com.example.paging_reserch.screen.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.paging_reserch.App
import com.example.paging_reserch.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatViewModel(
    savedStateHandle: SavedStateHandle,
    private val db: AppDatabase,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<ChatScreenDestination>()

    val pagingDataFlow: Flow<List<MessageItem>> = db.messageDao().read()
        .map { it.map { TextMessageItem(it) } }

}