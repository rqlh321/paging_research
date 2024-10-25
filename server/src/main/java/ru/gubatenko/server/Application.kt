package ru.gubatenko.server

import io.ktor.server.application.Application
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.gubatenko.server.data.DataStore
import ru.gubatenko.server.data.DataStoreHashMaps
import ru.gubatenko.server.data.UserWebSocketSessionController
import ru.gubatenko.server.domain.CreateMessageUseCase
import ru.gubatenko.server.domain.LoginUseCase
import ru.gubatenko.server.presentation.AuthRouting
import ru.gubatenko.server.presentation.ChatsRouting
import ru.gubatenko.server.presentation.MessagesRouting
import ru.gubatenko.server.presentation.WebSocketRouting

val dataModule = module {
    singleOf(::DataStoreHashMaps) { bind<DataStore>() }
    singleOf(::UserWebSocketSessionController)
}
val useCaseModule = module {
    singleOf(::CreateMessageUseCase)
    singleOf(::LoginUseCase)
}
val routingModule = module {
    singleOf(::AuthRouting)
    singleOf(::ChatsRouting)
    singleOf(::MessagesRouting)
    singleOf(::WebSocketRouting)

    single<List<RoutingSetup>> {
        listOf(
            get<AuthRouting>(),
            get<ChatsRouting>(),
            get<MessagesRouting>(),
            get<WebSocketRouting>(),
        )
    }
}

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}