package ru.gubatenko.message.local.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messagedatabaseentity ORDER BY timestamp DESC")
    fun read(): Flow<List<MessageDatabaseEntity>>

    @Query("SELECT messageId FROM messagedatabaseentity ORDER BY messageId LIMIT 1")
    suspend fun earliestPosition(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(messages: List<MessageDatabaseEntity>)

    @Query("DELETE FROM messagedatabaseentity WHERE chatId = :chatId")
    suspend fun delete(chatId: String)

}