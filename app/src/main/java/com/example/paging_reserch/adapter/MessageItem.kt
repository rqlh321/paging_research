package com.example.paging_reserch.adapter

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.example.paging_reserch.database.MessageDatabaseEntity
import java.text.SimpleDateFormat
import java.util.Locale

data class MessageItem(
    val id: Int,
    val time: String,
    val isWatched: Boolean,
) {
    val type = TYPE
    val corners = NOT_MY_MESSAGE_CORNERS

    constructor(entity: MessageDatabaseEntity) : this(
        id = entity.id,
        time = df.format(entity.timestamp),
        isWatched = entity.isWatched
    )

    companion object {
        private const val TYPE = "message"
        private val NOT_MY_MESSAGE_CORNERS = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp,
        )
        private val MY_MESSAGE_CORNERS = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp,
        )
        private val df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    }
}