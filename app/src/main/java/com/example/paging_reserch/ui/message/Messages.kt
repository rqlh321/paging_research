package com.example.paging_reserch.ui.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun Messages(
    messages: List<MessageItem>,
    onMessageClick: (MessageItem) -> Unit = {},
) {
    val listState = rememberLazyListState()

    if (messages.isNotEmpty()) {
        LaunchedEffect(Unit) {
            val index = messages.indexOfFirst { it.type == Const.NEW_DIVIDER } - 1
            if (index > 0) {
                listState.scrollToItem(index)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        reverseLayout = true,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            count = messages.size,
            key = { messages[it].id },
            contentType = { messages[it].type }
        ) {
            Message(messages[it], onMessageClick)
        }
    }
}