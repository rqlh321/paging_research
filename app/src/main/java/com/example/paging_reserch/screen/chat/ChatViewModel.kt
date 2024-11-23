package com.example.paging_reserch.screen.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.paging_reserch.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val args = savedStateHandle.toRoute<ChatScreenDestination>()

    val pagingDataFlow: Flow<List<MessageItem>> = App.db.messageDao().read()
        .map { it.map { TextMessageItem(it) } }

}