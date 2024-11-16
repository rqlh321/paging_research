package com.example.paging_reserch

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import ru.gubatenko.message.domain.impl.MessageDatabaseEntity

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
                messageId = it.toString(),
                timestamp = 0L,
                chatId = "",
            )
        }
        db.messageDao().update(messages)
        val messagesDb = db.messageDao().read().first()
        assertTrue(messagesDb.size == 101)
    }
}