package com.example.paging_reserch.screen.preset

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.paging_reserch.App
import com.example.paging_reserch.screen.chat.ChatDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChatPresetViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val mutableState = MutableStateFlow(ChatPresetScreenState())
    val state = mutableState
    private val channel = Channel<PresetChatAction>()
    val actions = channel.receiveAsFlow()


    init {
        App.chatRepo.chats()
            .onEach {
                val chat = it.firstOrNull()
                val buttons = if (chat == null) {
                    listOf(
                        ButtonItem(
                            text = "Создать чат",
                            onClick = ::createChat
                        )
                    )
                } else {
                    listOf(
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
                                        PresetChatAction.NavigateTo(ChatDestination(chat.id))
                                    )
                                }
                            }
                        )
                    )
                }

                mutableState.emit(state.first().copy(buttons = buttons))
            }
            .launchIn(viewModelScope)
    }

    fun emulateMessageReceive() {
        viewModelScope.launch {
//            val currentTimeMillis = System.currentTimeMillis()
//
//            val incomeMessages = (0 until 10).map {
//                MessageDatabaseEntity(
//                    id = (currentTimeMillis + it).toString(),
//                    chatId = CHAT_ID,
//                    timestamp = currentTimeMillis,
//                    isWatched = false
//                )
//            }
//            db.chatDao().updateFirstUnread(CHAT_ID, incomeMessages.last().id)
//            db.messageDao().update(incomeMessages)
        }
    }

    fun allMessagesWatched() {
        viewModelScope.launch {
//            db.chatDao().updateFirstUnread(CHAT_ID, "")
//            db.messageDao().markAsWatched()
        }
    }

    fun createChat() {
        viewModelScope.launch {
            App.chatRepo.create("test")
        }
    }
}