package com.example.paging_reserch.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM messagedatabaseentity ORDER BY timestamp DESC")
    fun read(): PagingSource<Int, MessageDatabaseEntity>

    @Query("SELECT * FROM messagedatabaseentity ORDER BY timestamp DESC")
    suspend fun readList(): List<MessageDatabaseEntity>

    @Query("SELECT messageId FROM messagedatabaseentity ORDER BY messageId LIMIT 1")
    suspend fun earliestPosition(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(messages: List<MessageDatabaseEntity>)

    @Query("DELETE FROM messagedatabaseentity WHERE chatId = :chatId")
    suspend fun delete(chatId: String)

}