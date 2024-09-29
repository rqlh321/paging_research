package com.example.paging_reserch.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.paging_reserch.database.MessageDatabaseEntity.Companion.MESSAGE_ID
import com.example.paging_reserch.database.MessageDatabaseEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [MESSAGE_ID]
)
data class MessageDatabaseEntity(
    @ColumnInfo(name = MESSAGE_ID) val id: Int,
    @ColumnInfo(name = TIMESTAMP) val timestamp: Long,
    @ColumnInfo(name = CHAT_ID) val chatId: String,
    @ColumnInfo(name = IS_WATCHED) val isWatched: Boolean,
) {
    companion object {
        const val TABLE_NAME = "message"

        const val MESSAGE_ID = "message_id"
        const val TIMESTAMP = "timestamp"
        const val CHAT_ID = "chat_id"
        const val IS_WATCHED = "is_watched"
    }
}