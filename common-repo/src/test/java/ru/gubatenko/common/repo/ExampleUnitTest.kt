package ru.gubatenko.common.repo

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gubatenko.common.Chat
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.Message
import ru.gubatenko.common.Messages
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server_api.ChatApi
import ru.gubatenko.server_api.MessageApi
import ru.gubatenko.server_api.socketFlow

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

            val chatId = chatApi.create(CreateChatBody("new")).id
            println(chatId)
            val message = messageApi.create(CreateMessageBody(chatId, "hola"))
            println(message)
            val messages = messageApi.messages(
                MessagesRout(
                    chatId = chatId,
                    messageId = null,
                    isDirectionToLatest = false,
                    limit = 100
                )
            )

            println(messages)
        }
    }

    @Test
    fun ws() {
        runBlocking {
            socketFlow()
                .take(2)
                .onEach { println(it) }
                .collect()

        }
    }
}