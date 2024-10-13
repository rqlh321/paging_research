package ru.gubatenko.common.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageDatabaseEntity(
    @PrimaryKey val messageId: String,
    val timestamp: Long,
    val chatId: String,
)