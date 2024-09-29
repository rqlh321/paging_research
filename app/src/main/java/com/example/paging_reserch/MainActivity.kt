package com.example.paging_reserch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.paging_reserch.adapter.MessageItem
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val listState = rememberLazyListState()
                    val pager = viewModel.pagingDataFlow.collectAsLazyPagingItems()

                    LazyColumn(
                        reverseLayout = true,
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        when (pager.loadState.prepend) {
                            is LoadState.Loading -> item { LoadingNextPageItem(modifier = Modifier) }

                            is LoadState.Error -> item {
                                ErrorMessage(
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
                            is LoadState.Loading -> item { PageLoader(modifier = Modifier.fillParentMaxSize()) }

                            is LoadState.Error -> item {
                                ErrorMessage(
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
                                if (item != null) {
                                    Message(item)
                                } else {
                                    MessageHolder()
                                }
                            }
                        }
                        when (pager.loadState.append) {
                            is LoadState.Loading -> item { LoadingNextPageItem(modifier = Modifier) }

                            is LoadState.Error -> item {
                                ErrorMessage(
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
        }
    }

    @Composable
    fun PageLoader(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.strFetchingDataFromServer),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            CircularProgressIndicator(Modifier.padding(top = 10.dp))
        }
    }

    @Composable
    fun LoadingNextPageItem(modifier: Modifier) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }

    @Composable
    fun ErrorMessage(
        message: String,
        modifier: Modifier = Modifier,
        onClickRetry: () -> Unit
    ) {
        Row(
            modifier = modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.weight(1f),
                maxLines = 2
            )
            OutlinedButton(onClick = onClickRetry) {
                Text(text = stringResource(id = R.string.strRetry))
            }
        }
    }

    @Composable
    private fun Message(item: MessageItem) {
        Column(
            modifier = Modifier
                .clip(item.corners)
                .background(if (item.isWatched) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
        ) {
            Text(
                text = item.id.toString(),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = item.time,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    @Composable
    private fun MessageHolder() {
        Spacer(
            modifier = Modifier
                .background(Color.Blue)
                .fillMaxWidth()
                .height(100.dp)
        )
    }

    @Composable
    private fun Control(
        viewModel: MainViewModel,
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

}