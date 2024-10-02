package com.example.paging_reserch.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.paging_reserch.screen.chat.compose.InitialPageLoading
import com.example.paging_reserch.screen.chat.compose.LoadingError
import com.example.paging_reserch.screen.chat.compose.Message
import com.example.paging_reserch.screen.chat.compose.NextPageLoading
import kotlinx.coroutines.launch

@Composable
fun ChatScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val listState = rememberLazyListState()
        val viewModel = viewModel<ChatViewModel>()

        val pager = viewModel.pagingDataFlow.collectAsLazyPagingItems()

        LazyColumn(
            reverseLayout = true,
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            when (pager.loadState.prepend) {
                is LoadState.Loading -> item { NextPageLoading(modifier = Modifier) }

                is LoadState.Error -> item {
                    LoadingError(
                        modifier = Modifier,
                        message = pager.loadState
                            .prepend
                            .let { it as LoadState.Error }
                            .error
                            .localizedMessage
                            .orEmpty(),
                        onClickRetry = { pager.retry() })
                }

                is LoadState.NotLoading -> Unit
            }
            when (pager.loadState.refresh) {
                is LoadState.Loading -> item { InitialPageLoading(modifier = Modifier.fillParentMaxSize()) }

                is LoadState.Error -> item {
                    LoadingError(
                        modifier = Modifier.fillParentMaxSize(),
                        message = pager.loadState.refresh.let { it as LoadState.Error }.error.localizedMessage!!,
                        onClickRetry = { pager.retry() })
                }

                is LoadState.NotLoading -> items(
                    count = pager.itemCount,
                    key = pager.itemKey { it.id },
                    contentType = pager.itemContentType { it.type }
                ) {
                    val item = pager[it]
                    Message(item, viewModel::onMessageClick)
                }
            }
            when (pager.loadState.append) {
                is LoadState.Loading -> item { NextPageLoading(modifier = Modifier) }

                is LoadState.Error -> item {
                    LoadingError(
                        modifier = Modifier,
                        message = pager.loadState
                            .append
                            .let { it as LoadState.Error }
                            .error
                            .localizedMessage
                            .orEmpty(),
                        onClickRetry = { pager.retry() })
                }

                is LoadState.NotLoading -> Unit
            }
        }

        Control(viewModel, listState)
    }
}

@Composable
private fun Control(
    viewModel: ChatViewModel,
    listState: LazyListState
) {
    val scope = rememberCoroutineScope()

    Column {
        Button(viewModel::allMessagesWatched) {
            Text("Отметить все сообщения прочитанными")
        }
        Button({ scope.launch { listState.animateScrollToItem(0, 0) } }) {
            Text("Прокрутить в начало")
        }
        Button(viewModel::emulateMessageReceive) {
            Text("Входящее сообщение")
        }
    }
}
