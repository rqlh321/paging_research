package ru.gubatenko.common.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Upsert
    suspend fun upsert(entity: ChatDatabaseEntity)

    @Upsert
    suspend fun upsert(entities: List<ChatDatabaseEntity>)

    @Query("SELECT * FROM chatdatabaseentity")
    fun getAllChatsFlow(): Flow<List<ChatDatabaseEntity>>

}