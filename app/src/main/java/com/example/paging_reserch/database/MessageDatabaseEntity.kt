package com.example.paging_reserch.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging_reserch.database.MessageDatabaseEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MessageDatabaseEntity(
    @PrimaryKey @ColumnInfo(name = POSITION) val position: Int,
    @ColumnInfo(name = CHAT_ID) val chatId: String,
    @ColumnInfo(name = IS_WATCHED) val isWatched: Boolean,
) {
    companion object {
        const val TABLE_NAME = "message"
        const val POSITION = "position"
        const val CHAT_ID = "chat_id"
        const val IS_WATCHED = "is_watched"
    }
}