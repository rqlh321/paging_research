package com.example.paging_reserch.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.paging_reserch.database.ChatDatabaseEntity.Companion.CHAT_ID
import com.example.paging_reserch.database.ChatDatabaseEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [CHAT_ID]
)
data class ChatDatabaseEntity(
    @ColumnInfo(name = CHAT_ID) val id: String,
) {
    companion object {
        const val TABLE_NAME = "chat"

        const val CHAT_ID = "chat_id"
    }
}