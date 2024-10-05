package com.example.paging_reserch

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paging_reserch.database.AppDatabase
import com.example.paging_reserch.database.MessageDatabaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val db: AppDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).build()

    @Test
    fun useAppContext() = runBlocking {
        val messages = (0..100).map {
            MessageDatabaseEntity(
                id = it.toString(),
                timestamp = 0L,
                chatId = "",
                isWatched = false
            )
        }
        db.messageDao().update(messages)
        val messagesDb = db.messageDao().readList()
        assertTrue(messagesDb.size == 101)
    }
}