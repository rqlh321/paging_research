package com.example.paging_reserch.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging_reserch.database.RemoteKeyDatabaseEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RemoteKeyDatabaseEntity(
    @PrimaryKey @ColumnInfo(name = CHAT_ID) val chatId: String,
    @ColumnInfo(name = NEXT_KEY) val nextKey: Int?
) {
    companion object {
        const val TABLE_NAME = "messages_remote_key"
        const val CHAT_ID = "chat_id"
        const val NEXT_KEY = "next_key"
    }
}