package com.example.paging_reserch

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gubatenko.chat.domain.impl.ChatDao
import ru.gubatenko.chat.domain.impl.ChatDatabaseEntity
import ru.gubatenko.message.domain.impl.MessageDao
import ru.gubatenko.message.domain.impl.MessageDatabaseEntity

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