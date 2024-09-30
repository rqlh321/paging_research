package com.example.paging_reserch.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun update(remoteKey: RemoteKeyDatabaseEntity)
    @Query("UPDATE messages_remote_key SET prepend_key = :key WHERE chat_id == :chatId")
    suspend fun updatePrepend(chatId: String, key: String?)
    @Query("UPDATE messages_remote_key SET append_key = :key WHERE chat_id == :chatId")
    suspend fun updateAppend(chatId: String, key: String?)

    @Query("SELECT * FROM ${RemoteKeyDatabaseEntity.TABLE_NAME} WHERE ${RemoteKeyDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun remoteKey(chatId: String): RemoteKeyDatabaseEntity?

    @Query("DELETE FROM ${RemoteKeyDatabaseEntity.TABLE_NAME} WHERE ${RemoteKeyDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun delete(chatId: String)
}