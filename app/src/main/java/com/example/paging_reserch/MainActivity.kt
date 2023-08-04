package com.example.paging_reserch

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paging_reserch.adapter.MessageAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val messagesAdapter = MessageAdapter(viewModel::messageWatched)
        val recyclerView = findViewById<RecyclerView>(R.id.messages_id)
        val recyclerLayoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.adapter = messagesAdapter
        recyclerView.setHasFixedSize(true)

        val allWatchedButton = findViewById<Button>(R.id.all_watched_id)
        allWatchedButton.setOnClickListener { viewModel.allMessagesWatched() }

        val toBottom = findViewById<Button>(R.id.scroll_to_bottom_id)
        toBottom.setOnClickListener { recyclerView.smoothScrollToPosition(0) }

        val sendButton = findViewById<Button>(R.id.send_message_id)
        sendButton.setOnClickListener { viewModel.emulateMessageReceive() }

        viewModel.positionToScroll
            .flowWithLifecycle(lifecycle)
            .onEach { unwatchedCount ->
                toBottom.text = "Вниз (Непрочитанных сообщений $unwatchedCount)"
                val firstVisibleItemPosition = recyclerLayoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition < 2) {
                    recyclerView.smoothScrollToPosition(0)
                }
            }
            .launchIn(lifecycleScope)

        viewModel.pagingDataFlow
            .flowWithLifecycle(lifecycle)
            .onEach(messagesAdapter::submitData)
            .launchIn(lifecycleScope)

    }

}