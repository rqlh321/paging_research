package com.example.paging_reserch.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM ${MessageDatabaseEntity.TABLE_NAME} ORDER BY ${MessageDatabaseEntity.TIMESTAMP} DESC")
    fun read(): PagingSource<Int, MessageDatabaseEntity>

    @Query("SELECT * FROM ${MessageDatabaseEntity.TABLE_NAME} ORDER BY ${MessageDatabaseEntity.TIMESTAMP} DESC")
    suspend fun readList(): List<MessageDatabaseEntity>

    @Query("SELECT ${MessageDatabaseEntity.MESSAGE_ID} FROM ${MessageDatabaseEntity.TABLE_NAME} ORDER BY ${MessageDatabaseEntity.MESSAGE_ID} LIMIT 1")
    suspend fun earliestPosition(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(messages: List<MessageDatabaseEntity>)

    @Query("DELETE FROM ${MessageDatabaseEntity.TABLE_NAME} WHERE ${MessageDatabaseEntity.CHAT_ID} = :chatId")
    suspend fun delete(chatId: String)

}