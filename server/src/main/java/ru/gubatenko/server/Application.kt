package ru.gubatenko.server

import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.gubatenko.server.data.ChatRepositoryImpl
import ru.gubatenko.server.data.MessageRepositoryImpl
import ru.gubatenko.server.data.UserWebSocketSessionController
import ru.gubatenko.server.domain.ChatRepository
import ru.gubatenko.server.domain.CreateMessageUseCase
import ru.gubatenko.server.domain.LoginUseCase
import ru.gubatenko.server.domain.MessageRepository
import ru.gubatenko.server.presentation.AuthRouting
import ru.gubatenko.server.presentation.ChatsRouting
import ru.gubatenko.server.presentation.MessagesRouting
import ru.gubatenko.server.presentation.WebSocketRouting

val dataModule = module {
    single {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "mysecretpassword"
        )
    }
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
    singleOf(::MessageRepositoryImpl) { bind<MessageRepository>() }

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