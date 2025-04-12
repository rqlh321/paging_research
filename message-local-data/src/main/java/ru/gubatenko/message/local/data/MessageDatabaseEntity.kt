package ru.gubatenko.message.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageDatabaseEntity(
    @PrimaryKey val messageId: String,
    val timestamp: Long,
    val chatId: String,
)