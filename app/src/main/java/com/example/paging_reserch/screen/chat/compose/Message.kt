package com.example.paging_reserch.screen.chat.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.paging_reserch.screen.chat.DateMessageItem
import com.example.paging_reserch.screen.chat.DividerMessageItem
import com.example.paging_reserch.screen.chat.MessageItem
import com.example.paging_reserch.screen.chat.TextMessageItem

@Composable
fun Message(
    item: MessageItem?,
    click: (MessageItem) -> Unit
) {
    when (item) {
        is DateMessageItem -> {
            Text(
                text = item.date,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        is DividerMessageItem -> {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = item.text,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        is TextMessageItem -> {
            Text(
                modifier = Modifier
                    .clip(item.corners)
                    .background(if (item.isWatched) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable { click(item) }
                    .padding(16.dp),
                text = item.time,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        null -> Spacer(
            modifier = Modifier
                .background(Color.Blue)
                .fillMaxWidth()
                .height(100.dp)
        )
    }

}