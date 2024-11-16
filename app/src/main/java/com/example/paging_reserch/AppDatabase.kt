package com.example.paging_reserch

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gubatenko.common.database.ChatDao
import ru.gubatenko.common.database.ChatDatabaseEntity
import ru.gubatenko.common.database.MessageDao
import ru.gubatenko.common.database.MessageDatabaseEntity

@Database(
    entities = [
        ChatDatabaseEntity::class,
        MessageDatabaseEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
}