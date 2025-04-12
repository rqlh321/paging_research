package com.example.paging_reserch

import android.app.Application
import com.example.paging_reserch.screen.root.RootScreenViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.channels.Channel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.gubatenko.api.impl.apiModule
import ru.gubatenko.app.core.android.base
import ru.gubatenko.app.core.android.databaseModule
import ru.gubatenko.app.navigation.RootRout
import ru.gubatenko.auth.feature.authModule
import ru.gubatenko.domain.auth.IsLoginAvailableUseCase
import ru.gubatenko.domain.auth.LoginUseCase
import ru.gubatenko.domain.auth.LogoutUseCase
import ru.gubatenko.domain.auth.impl.IsLoginAvailableUseCaseImpl
import ru.gubatenko.domain.auth.impl.LoginUseCaseImpl
import ru.gubatenko.domain.auth.impl.LogoutUseCaseImpl
import ru.gubatenko.server_api.Client
import ru.gubatenko.server_api.ClientConfig
import ru.gubatenko.user.domain.CreateAccountUseCase
import ru.gubatenko.user.domain.DeleteAccountUseCase
import ru.gubatenko.user.domain.IsCreateAccountAvailableUseCase
import ru.gubatenko.user.domain.impl.CreateAccountUseCaseImpl
import ru.gubatenko.user.domain.impl.DeleteAccountUseCaseImpl
import ru.gubatenko.user.domain.impl.IsCreateAccountAvailableUseCaseImpl
import ru.gubatenko.user.profile.feature.userProfileModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                base,
                networkTransportModule,
                databaseModule,
                apiModule,
                useCase,
                rootModule,
                authModule,
                userProfileModule
            )
        }
    }
}

val networkTransportModule = module {
    single { ClientConfig() }
    singleOf(::Client)
    single<HttpClient> { get<Client>().httpClient }
}

val useCase = module {
    factoryOf(::IsLoginAvailableUseCaseImpl) { bind<IsLoginAvailableUseCase>() }
    factoryOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
    factoryOf(::LogoutUseCaseImpl) { bind<LogoutUseCase>() }

    factoryOf(::IsCreateAccountAvailableUseCaseImpl) { bind<IsCreateAccountAvailableUseCase>() }
    factoryOf(::CreateAccountUseCaseImpl) { bind<CreateAccountUseCase>() }
    factoryOf(::DeleteAccountUseCaseImpl) { bind<DeleteAccountUseCase>() }
}

val rootModule = module {
    single { Channel<RootRout>() }
    viewModelOf(::RootScreenViewModel)
}