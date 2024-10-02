package com.example.paging_reserch.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ChatDao {
    @Upsert
    suspend fun update(entity: ChatDatabaseEntity)

    @Query("UPDATE chat SET first_unread_message_id = :messageId WHERE chat_id == :chatId")
    suspend fun updateFirstUnread(chatId: String, messageId: String?)

    @Query("SELECT * FROM ${ChatDatabaseEntity.TABLE_NAME} WHERE ${ChatDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun get(chatId: String): ChatDatabaseEntity
}