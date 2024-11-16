package com.example.paging_reserch

import android.app.Application
import androidx.room.Room

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var db: AppDatabase
    }
}