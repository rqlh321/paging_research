package com.example.paging_reserch

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.server_api.ChatApi

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
            println(api.create(CreateChatBody("new")))
            println(api.chats())
        }
    }
}