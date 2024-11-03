package ru.gubatenko.common.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import ru.gubatenko.common.AuthBody
import ru.gubatenko.common.ChatId
import ru.gubatenko.common.CreateMessageBody
import ru.gubatenko.server_api.AuthApi
import ru.gubatenko.server_api.MessageApi
import ru.gubatenko.server_api.socketFlow
import ru.gubatenko.server_api.token

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val authApi = AuthApi()
    val messageApi = MessageApi()
    val body = CreateMessageBody(
        chatId = ChatId("fe35b7c4-1f47-46da-b07d-3312962893d9"),
        text = "hola"
    )

    @Test
    fun test1() {
        runBlocking {
            val result = authApi.auth(AuthBody("test", "test"))
            token = result.accessToken
        }
    }

    @Test
    fun test2() {
        runBlocking {
            val flow = socketFlow()
                .onEach {
                    println(it)
                    cancel()
                }
                .launchIn(CoroutineScope(currentCoroutineContext()))
            messageApi.create(body)
            flow.join()
        }
    }

    @Test
    fun test3() {
        runBlocking {
            val flow = socketFlow()
                .onEach {
                    println(it)
                    cancel()
                }
                .launchIn(CoroutineScope(currentCoroutineContext()))
            messageApi.create(body)
            flow.join()
        }
    }

    @Test
    @Ignore
    fun ws() {
        val authApi = AuthApi()
        runBlocking {
            socketFlow()
                .onEach { println(it) }
                .collect()
        }
    }
}