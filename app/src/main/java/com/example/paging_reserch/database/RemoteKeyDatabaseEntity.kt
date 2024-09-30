package com.example.paging_reserch.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paging_reserch.database.RemoteKeyDatabaseEntity.Companion.CHAT_ID
import com.example.paging_reserch.database.RemoteKeyDatabaseEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [CHAT_ID]
    )
data class RemoteKeyDatabaseEntity(
    @ColumnInfo(name = CHAT_ID) val chatId: String,
    @ColumnInfo(name = PREPEND_KEY) val prependKey: String?,
    @ColumnInfo(name = APPEND_KEY) val appendKey: String?,
) {
    companion object {
        const val TABLE_NAME = "messages_remote_key"

        const val CHAT_ID = "chat_id"
        const val PREPEND_KEY = "prepend_key"
        const val APPEND_KEY = "append_key"
    }
}