package com.example.paging_reserch

import android.app.Application
import androidx.room.Room
import com.example.paging_reserch.database.AppDatabase
import ru.gubatenko.common.repo.ChatRepo
import ru.gubatenko.common.repo.MessageRepo

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
            .fallbackToDestructiveMigration()
            .build()
        chatRepo = ChatRepo(db.chatDao())
        messageRepo = MessageRepo(
            remoteKeyDao = db.remoteKeyDao(),
            messageDao = db.messageDao()
        )
    }

    companion object {
        lateinit var db: AppDatabase
        lateinit var chatRepo: ChatRepo
        lateinit var messageRepo: MessageRepo
    }
}