package com.example.paging_reserch

import android.app.Application
import androidx.room.Room
import net.sqlcipher.database.SupportFactory


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
    }

    companion object {
        lateinit var db: AppDatabase
    }
}