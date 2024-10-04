package com.example.paging_reserch.screen.preset

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatPresetsScreen(
    toChat: () -> Unit = {}
) {
    val viewModel = viewModel<ChatPresetViewModel>()

    Column {
        Button({ viewModel.createChat() }) { Text("Создать чат") }
        Button({ viewModel.allMessagesWatched() }) { Text("Отметить все сообщения прочитанными") }
        Button({ viewModel.emulateMessageReceive(1) }) { Text("Получить сообщение") }
        Button({ viewModel.emulateMessageReceive(10) }) { Text("Получить 10 сообщений") }
        Button({ viewModel.emulateMessageReceive(100) }) { Text("Получить 100 сообщений") }
        Button(toChat) { Text("Открыть чат") }
    }
}