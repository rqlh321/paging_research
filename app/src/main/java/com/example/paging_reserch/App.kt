package com.example.paging_reserch

import android.app.Application
import androidx.room.Room
import com.example.paging_reserch.screen.Destination
import kotlinx.coroutines.channels.Channel
import net.sqlcipher.database.SupportFactory
import ru.gubatenko.credential.store.TokenStore


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val builder = Room.databaseBuilder(
            context = this,
            klass = AppDatabase::class.java,
            name = "database-name"
        ).fallbackToDestructiveMigration()

        val repo = PassphraseRepository(this)
        builder.openHelperFactory(SupportFactory(repo.getPassphrase()))
        db = builder.build()
        tokenStore = TokenStoreImpl(this)
    }

    companion object {
        lateinit var tokenStore: TokenStore
        lateinit var db: AppDatabase
        val router = Channel<Destination>()
    }
}