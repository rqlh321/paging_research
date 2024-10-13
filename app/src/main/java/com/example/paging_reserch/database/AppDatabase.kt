package com.example.paging_reserch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gubatenko.common.database.ChatDao
import ru.gubatenko.common.database.ChatDatabaseEntity
import ru.gubatenko.common.database.MessageDao
import ru.gubatenko.common.database.MessageDatabaseEntity
import ru.gubatenko.common.database.RemoteKeyDao
import ru.gubatenko.common.database.RemoteKeyDatabaseEntity

@Database(
    entities = [
        ChatDatabaseEntity::class,
        MessageDatabaseEntity::class,
        RemoteKeyDatabaseEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun pageDao(): PageDao
}