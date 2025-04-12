package ru.gubatenko.domain.impl

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gubatenko.auth.data.LoginBody
import ru.gubatenko.common.Password
import ru.gubatenko.common.Username
import ru.gubatenko.domain.auth.impl.AuthApiImpl
import ru.gubatenko.server_api.Client
import ru.gubatenko.server_api.ClientConfig

class ApiTest {
    val tokenStore = FakeTokenStore()
    val clientConfig = ClientConfig()
    val client = Client(
        config = clientConfig,
        tokenStore = tokenStore
    )
    val api = AuthApiImpl(client.httpClient)

    @Test
    fun loginTest() {
        runBlocking {
            try {
                api.login(
                    LoginBody(
                        Username("2"),
                        Password("2"),
                    )
                )
                assert(true)
            } catch (e: Exception) {
                e.printStackTrace()
                assert(false)
            }

        }
    }
}