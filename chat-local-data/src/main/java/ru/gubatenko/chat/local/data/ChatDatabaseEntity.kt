package ru.gubatenko.chat.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatDatabaseEntity(
    @PrimaryKey val id: String,
)