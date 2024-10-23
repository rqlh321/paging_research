package ru.gubatenko.server

import io.ktor.server.application.Application
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.gubatenko.server.data.DataStore
import ru.gubatenko.server.data.DataStoreHashMaps
import ru.gubatenko.server.domain.CreateMessageUseCase
import ru.gubatenko.server.plugins.configureRouting
import ru.gubatenko.server.session.UserWebSocketSessionController

val appModule = module {
    singleOf(::DataStoreHashMaps) { bind<DataStore>() }
    singleOf(::UserWebSocketSessionController)
    singleOf(::CreateMessageUseCase)
}

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
