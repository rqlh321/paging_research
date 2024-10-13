package com.example.paging_reserch.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Upsert
    suspend fun update(entity: ChatDatabaseEntity)

    @Query("SELECT * FROM ${ChatDatabaseEntity.TABLE_NAME} WHERE ${ChatDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun get(chatId: String): ChatDatabaseEntity

    @Query("SELECT * FROM ${ChatDatabaseEntity.TABLE_NAME}")
    fun getAllChatsFlow(): Flow<List<ChatDatabaseEntity>>

}