package com.example.paging_reserch.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import ru.gubatenko.common.database.MessageDatabaseEntity

@Dao
interface PageDao {
    @Query("SELECT * FROM messagedatabaseentity ORDER BY timestamp DESC")
    fun read(): PagingSource<Int, MessageDatabaseEntity>

}