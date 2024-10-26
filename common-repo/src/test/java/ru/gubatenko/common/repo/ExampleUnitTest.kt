package ru.gubatenko.common.repo

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.CreateChatBody
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.common.MessagesRout
import ru.gubatenko.server_api.AuthApi
import ru.gubatenko.server_api.ChatApi
import ru.gubatenko.server_api.MessageApi
import ru.gubatenko.server_api.socketFlow
import ru.gubatenko.server_api.token

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        runBlocking {
            val authApi = AuthApi()
            val chatApi = ChatApi()
            val messageApi = MessageApi()

            val result = authApi.auth(AuthBody("test", "test"))
            token = result.accessToken
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
        val authApi = AuthApi()
        runBlocking {
            val result = authApi.auth(AuthBody("test", "test"))
            socketFlow(result.accessToken)
                .onEach { println(it) }
                .collect()

        }
    }
}