package ru.gubatenko.server

import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.gubatenko.server.data.credentials.CredentialRepositoryImpl
import ru.gubatenko.server.data.chat.ChatRepositoryImpl
import ru.gubatenko.server.data.message.MessageRepositoryImpl
import ru.gubatenko.server.data.UserWebSocketSessionController
import ru.gubatenko.server.data.DaoStore
import ru.gubatenko.server.data.user.UserRepositoryImpl
import ru.gubatenko.server.domain.repo.ChatRepository
import ru.gubatenko.server.domain.repo.CredentialRepository
import ru.gubatenko.server.domain.usecase.CreateMessageUseCase
import ru.gubatenko.server.domain.usecase.LoginUseCase
import ru.gubatenko.server.domain.usecase.TokenValidateUseCase
import ru.gubatenko.server.domain.repo.MessageRepository
import ru.gubatenko.server.domain.repo.UserRepository
import ru.gubatenko.server.presentation.AuthRouting
import ru.gubatenko.server.presentation.ChatsRouting
import ru.gubatenko.server.presentation.MessagesRouting
import ru.gubatenko.server.presentation.UserRouting
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
    singleOf(::DaoStore)
    singleOf(::CredentialRepositoryImpl) { bind<CredentialRepository>() }
    singleOf(::ChatRepositoryImpl) { bind<ChatRepository>() }
    singleOf(::MessageRepositoryImpl) { bind<MessageRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }

    singleOf(::UserWebSocketSessionController)
}
val useCaseModule = module {
    singleOf(::TokenValidateUseCase)
    singleOf(::CreateMessageUseCase)
    singleOf(::LoginUseCase)
}
val routingModule = module {
    singleOf(::AuthRouting)
    singleOf(::UserRouting)
    singleOf(::ChatsRouting)
    singleOf(::MessagesRouting)
    singleOf(::WebSocketRouting)

    single<List<RoutingSetup>> {
        listOf(
            get<AuthRouting>(),
            get<UserRouting>(),
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