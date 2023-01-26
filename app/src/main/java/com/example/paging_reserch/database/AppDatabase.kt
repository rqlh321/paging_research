package com.example.paging_reserch.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MessageDatabaseEntity::class,
        RemoteKeyDatabaseEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}