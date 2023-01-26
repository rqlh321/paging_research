package com.example.paging_reserch.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM ${MessageDatabaseEntity.TABLE_NAME} ORDER BY ${MessageDatabaseEntity.POSITION}")
    fun read(): PagingSource<Int, MessageDatabaseEntity>

    @Query("SELECT ${MessageDatabaseEntity.POSITION} FROM ${MessageDatabaseEntity.TABLE_NAME} ORDER BY ${MessageDatabaseEntity.POSITION} LIMIT 1")
    suspend fun earliestPosition(): Int

    @Query("UPDATE ${MessageDatabaseEntity.TABLE_NAME} SET ${MessageDatabaseEntity.IS_WATCHED} = 1")
    suspend fun markAsWatched(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(messages: List<MessageDatabaseEntity>)

    @Query("DELETE FROM ${MessageDatabaseEntity.TABLE_NAME} WHERE ${MessageDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun delete(chatId: String)

    @Query("SELECT * FROM ${MessageDatabaseEntity.TABLE_NAME} WHERE ${MessageDatabaseEntity.IS_WATCHED} = 0")
    fun notWatchedMessagesFlow(): Flow<List<MessageDatabaseEntity>>

    @Query("UPDATE ${MessageDatabaseEntity.TABLE_NAME} SET ${MessageDatabaseEntity.IS_WATCHED} = 1 WHERE ${MessageDatabaseEntity.POSITION} = :position")
    suspend fun watched(position: Int)

}