package com.example.paging_reserch

import android.app.Application
import androidx.room.Room
import com.example.paging_reserch.screen.root.RootScreenViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.channels.Channel
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.gubatenko.app.navigation.RootRout
import ru.gubatenko.auth.feature.authModule
import ru.gubatenko.credential.store.TokenStore
import ru.gubatenko.domain.auth.IsLoginAvailableUseCase
import ru.gubatenko.domain.auth.LoginUseCase
import ru.gubatenko.domain.auth.LogoutUseCase
import ru.gubatenko.domain.auth.impl.AuthApi
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
import ru.gubatenko.user.domain.impl.UserApi
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

val base = module {
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }
}

val networkTransportModule = module {
    single { ClientConfig() }
    singleOf(::Client)
    single<HttpClient> { get<Client>().httpClient }
}
val apiModule = module {
    singleOf(::AuthApi)
    singleOf(::UserApi)
}
val useCase = module {
    factoryOf(::IsLoginAvailableUseCaseImpl) { bind<IsLoginAvailableUseCase>() }
    factoryOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
    factoryOf(::LogoutUseCaseImpl) { bind<LogoutUseCase>() }

    factoryOf(::IsCreateAccountAvailableUseCaseImpl) { bind<IsCreateAccountAvailableUseCase>() }
    factoryOf(::CreateAccountUseCaseImpl) { bind<CreateAccountUseCase>() }
    factoryOf(::DeleteAccountUseCaseImpl) { bind<DeleteAccountUseCase>() }
}
val databaseModule = module {
    singleOf(::PassphraseRepository)
    single<AppDatabase> {
        val builder = Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "database-name"
        ).fallbackToDestructiveMigration()
        val repo = get<PassphraseRepository>()
        val factory = SupportFactory(repo.getPassphrase())
        builder.openHelperFactory(factory)
        builder.build()
    }
}

val rootModule = module {
    single { Channel<RootRout>() }
    viewModelOf(::RootScreenViewModel)
}