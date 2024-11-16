package ru.gubatenko.chat.domain.impl

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatDatabaseEntity(
    @PrimaryKey val id: String,
)