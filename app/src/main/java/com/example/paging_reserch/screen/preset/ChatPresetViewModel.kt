package com.example.paging_reserch.screen.preset

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.ChatDatabaseEntity
import com.example.paging_reserch.database.MessageDatabaseEntity
import kotlinx.coroutines.launch

class ChatPresetViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {


    private val db = Room.databaseBuilder(app, AppDatabase::class.java, "database-name").build()
    private val messageDao = db.messageDao()
    private val chatDao = db.chatDao()

    fun emulateMessageReceive(count: Int) {
        viewModelScope.launch {
            val currentTimeMillis = System.currentTimeMillis()
            val firstUnreadMessageId = currentTimeMillis.toString()
            chatDao.updateFirstUnread(CHAT_ID, firstUnreadMessageId)

            val incomeMessages = (0 until count).map {
                MessageDatabaseEntity(
                    id = (currentTimeMillis + it).toString(),
                    chatId = CHAT_ID,
                    timestamp = currentTimeMillis,
                    isWatched = false
                )
            }
            messageDao.update(incomeMessages)
        }
    }

    fun allMessagesWatched() {
        viewModelScope.launch {
            chatDao.updateFirstUnread(CHAT_ID, "")
            messageDao.markAsWatched()
        }
    }

    fun createChat() {
        viewModelScope.launch {
            chatDao.update(
                ChatDatabaseEntity(
                    id = CHAT_ID,
                    firstUnreadMessageId = ""
                )
            )
        }
    }

    companion object {
        const val CHAT_ID = "0"
    }
}