package ru.gubatenko.server.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import ru.gubatenko.server.data.DataStore

fun Application.configureRouting(dataStore: DataStore) {
    install(Resources)
    install(ContentNegotiation) { json() }
    routing {
        chats(dataStore)
        messages(dataStore)
    }
}