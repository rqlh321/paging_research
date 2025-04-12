package ru.gubatenko.app.core.android

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gubatenko.chat.local.data.ChatDao
import ru.gubatenko.chat.local.data.ChatDatabaseEntity
import ru.gubatenko.message.local.data.MessageDao
import ru.gubatenko.message.local.data.MessageDatabaseEntity

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