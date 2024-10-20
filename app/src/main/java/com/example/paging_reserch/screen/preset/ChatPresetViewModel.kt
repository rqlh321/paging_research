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
                            onClick = {
                                viewModelScope.launch {
                                    App.chatRepo.create("test")
                                }
                            }
                        )
                    )
                } else {
                    listOf(
                        ButtonItem(
                            text = "Открыть чат",
                            onClick = {
                                viewModelScope.launch {
                                    channel.send(PresetChatAction.NavigateTo(ChatDestination(chat.id)))
                                }
                            }
                        )
                    )
                }

                mutableState.emit(state.first().copy(buttons = buttons))
            }
            .launchIn(viewModelScope)
    }
}