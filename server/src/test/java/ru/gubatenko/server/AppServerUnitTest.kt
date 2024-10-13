package ru.gubatenko.server

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import ru.gubatenko.server.data.DataStoreHashMaps
import ru.gubatenko.server.plugins.configureRouting
import kotlin.test.Test
import kotlin.test.assertEquals

class AppServerUnitTest {
    @Test
    fun testRoot() = testApplication {
        val store = DataStoreHashMaps()
        application {
            configureRouting(store)
        }
        val response = client.get("/chats")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}