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
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.gubatenko.app.navigation.RootRout
import ru.gubatenko.auth.feature.authModule
import ru.gubatenko.server_api.Client
import ru.gubatenko.server_api.ClientConfig
import ru.gubatenko.user.profile.feature.userProfileModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkTransportModule,
                databaseModule,

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