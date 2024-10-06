package com.example.paging_reserch

import com.example.paging_reserch.network.ChatApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        runBlocking {
            val api = ChatApi()
            val chats = api.chats()
            println(chats)
        }
    }
}