package com.example.paging_reserch

import android.app.Application
import androidx.room.Room
import com.example.paging_reserch.screen.Destination
import com.example.paging_reserch.screen.auth.AuthViewModel
import com.example.paging_reserch.screen.chat.ChatViewModel
import com.example.paging_reserch.screen.root.RootScreenViewModel
import kotlinx.coroutines.channels.Channel
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.gubatenko.credential.store.TokenStore

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                navigationModule,
                tokenModule,
                databaseModule,
                authModule
            )
        }
    }
}

val navigationModule = module {
    single { Channel<Destination>() }
}
val tokenModule = module {
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }
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

val authModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::RootScreenViewModel)
}