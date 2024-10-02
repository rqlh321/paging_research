package com.example.paging_reserch.screen.chat

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.example.paging_reserch.database.MessageDatabaseEntity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

sealed class MessageItem {
    abstract val id: String
    abstract val type: String
}

data class DateMessageItem(
    override val id: String = UUID.randomUUID().toString(),
    val date: String,
) : MessageItem() {
    override val type = Const.DATE
}

data class DividerMessageItem(
    override val id: String = UUID.randomUUID().toString(),
    val text: String,
) : MessageItem() {
    override val type = Const.DIVIDER
}

data class TextMessageItem(
    override val id: String,
    val date: String,
    val time: String,
    val isWatched: Boolean,
) : MessageItem() {
    override val type = Const.MESSAGE
    val corners = Const.NOT_MY_MESSAGE_CORNERS

    constructor(entity: MessageDatabaseEntity) : this(
        id = entity.id,
        date = Const.date.format(entity.timestamp),
        time = Const.time.format(entity.timestamp),
        isWatched = entity.isWatched
    )

}

object Const {
    const val MESSAGE = "message"
    const val DIVIDER = "divider"
    const val DATE = "date"
    val NOT_MY_MESSAGE_CORNERS = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomEnd = 16.dp,
    )
    val MY_MESSAGE_CORNERS = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 16.dp,
    )
    val time = SimpleDateFormat("HH:mm:ss", Locale.US)
    val date = SimpleDateFormat("dd.MM.yyyy", Locale.US)
}