package ru.gubatenko.common.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun update(remoteKey: RemoteKeyDatabaseEntity)

    @Query("UPDATE remotekeydatabaseentity SET prependKey = :key WHERE chatId == :chatId")
    suspend fun updatePrepend(chatId: String, key: String?)

    @Query("UPDATE remotekeydatabaseentity SET appendKey = :key WHERE chatId == :chatId")
    suspend fun updateAppend(chatId: String, key: String?)

    @Query("SELECT * FROM remotekeydatabaseentity WHERE chatId = :chatId")
    suspend fun remoteKey(chatId: String): RemoteKeyDatabaseEntity?

    @Query("DELETE FROM remotekeydatabaseentity WHERE chatId = :chatId")
    suspend fun delete(chatId: String)
}