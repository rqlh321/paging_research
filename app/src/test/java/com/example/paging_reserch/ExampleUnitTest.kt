package com.example.paging_reserch

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server_api.ChatApi
import ru.gubatenko.server_api.MessageApi

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        runBlocking {
            val chatApi = ChatApi()
            val messageApi = MessageApi()

            val chatId = chatApi.create(CreateChatBody("new"))
            println(chatId)
            messageApi.create(CreateMessageBody(chatId, "hola"))
            val messages = messageApi.all(MessagesRout(chatId))

            println(messages)
        }
    }
}