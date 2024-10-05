package com.example.paging_reserch.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.paging_reserch.screen.chat.compose.InitialPageLoading
import com.example.paging_reserch.screen.chat.compose.LoadingError
import com.example.paging_reserch.screen.chat.compose.Message
import com.example.paging_reserch.screen.chat.compose.NextPageLoading

@Composable
fun ChatScreen() {
    val viewModel = viewModel<ChatViewModel>()
    val pager = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    ChatScreenContent(
        pager = pager,
        onMessageClick = viewModel::onMessageClick,
    )
}

@Composable
private fun ChatScreenContent(
    pager: LazyPagingItems<MessageItem>,
    onMessageClick: (MessageItem) -> Unit = {},
) {
    val listState = rememberLazyListState()

    if (pager.itemCount > 0) {
        LaunchedEffect(Unit) {
            val index = pager.itemSnapshotList.items.indexOfFirst { it.type == Const.NEW_DIVIDER } - 1
            if (index > 0) {
                listState.scrollToItem(index)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    Message(pager[it], onMessageClick)
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
    }
}

