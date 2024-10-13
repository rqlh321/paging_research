package com.example.paging_reserch.screen.preset

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.ChatDatabaseEntity
import com.example.paging_reserch.database.MessageDatabaseEntity
import com.example.paging_reserch.screen.chat.ChatDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.gubatenko.server_api.ChatApi

class ChatPresetViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val db = Room.databaseBuilder(app, AppDatabase::class.java, "database-name").build()

    private val mutableState = MutableStateFlow(ChatPresetScreenState())
    val state = mutableState
    private val channel = Channel<PresetChatAction>()
    val actions = channel.receiveAsFlow()

    val api = ChatApi()

    init {
        db.chatDao()
            .getAllChatsFlow()
            .onEach {
                val chat = it.firstOrNull()
                val value = if (chat == null) {
                    state.first().copy(
                        buttons = listOf(
                            ButtonItem(
                                text = "Создать чат",
                                onClick = ::createChat
                            ),
                        )
                    )
                } else {
                    state.first().copy(
                        buttons = listOf(
                            ButtonItem(
                                text = "Отметить все сообщения прочитанными",
                                onClick = ::allMessagesWatched
                            ),
                            ButtonItem(
                                text = "Получить 10 сообщений",
                                onClick = ::emulateMessageReceive
                            ),
                            ButtonItem(
                                text = "Открыть чат",
                                onClick = {
                                    viewModelScope.launch {
                                        channel.send(
                                            PresetChatAction.NavigateTo(
                                                ChatDestination(
                                                    chat.id
                                                )
                                            )
                                        )
                                    }
                                }
                            )
                        )
                    )
                }
                mutableState.emit(value)
            }
            .launchIn(viewModelScope)
    }

    fun emulateMessageReceive() {
        viewModelScope.launch {
            val currentTimeMillis = System.currentTimeMillis()

            val incomeMessages = (0 until 10).map {
                MessageDatabaseEntity(
                    id = (currentTimeMillis + it).toString(),
                    chatId = CHAT_ID,
                    timestamp = currentTimeMillis,
                    isWatched = false
                )
            }
            db.chatDao().updateFirstUnread(CHAT_ID, incomeMessages.last().id)
            db.messageDao().update(incomeMessages)
        }
    }

    fun allMessagesWatched() {
        viewModelScope.launch {
            db.chatDao().updateFirstUnread(CHAT_ID, "")
            db.messageDao().markAsWatched()
        }
    }

    fun createChat() {
        viewModelScope.launch {
            db.chatDao().update(
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