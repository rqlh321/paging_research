package ru.gubatenko.user.domain.impl

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gubatenko.server_api.Client
import ru.gubatenko.server_api.ClientConfig

class UserTest {
    val tokenStore = FakeTokenStore()
    val clientConfig = ClientConfig()
    val client = Client(
        config = clientConfig,
        tokenStore = tokenStore
    )
    val api = UserApiImpl(client.httpClient)

    @Test
    fun loginTest() {
        runBlocking {
            val logout = api.logout()
            assert(logout.status == HttpStatusCode.OK)
        }
    }
}