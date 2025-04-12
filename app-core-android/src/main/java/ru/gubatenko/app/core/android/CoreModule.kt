package ru.gubatenko.app.core.android

import androidx.room.Room
import net.sqlcipher.database.SupportFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.gubatenko.credential.store.PassphraseRepository
import ru.gubatenko.credential.store.TokenStore


val base = module {
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }
}
val databaseModule = module {
    singleOf(::PassphraseRepositoryImpl) { bind<PassphraseRepository>() }
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