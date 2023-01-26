package com.example.paging_reserch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(remoteKey: RemoteKeyDatabaseEntity)

    @Query("SELECT ${RemoteKeyDatabaseEntity.NEXT_KEY} FROM ${RemoteKeyDatabaseEntity.TABLE_NAME} WHERE ${RemoteKeyDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun nextKeyForChatById(chatId: String): Int?

    @Query("DELETE FROM ${RemoteKeyDatabaseEntity.TABLE_NAME} WHERE ${RemoteKeyDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun delete(chatId: String)
}