package com.example.paging_reserch.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatDatabaseEntity(
    @PrimaryKey val id: String,
)