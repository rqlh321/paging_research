package ru.gubatenko.common.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyDatabaseEntity(
    @PrimaryKey val chatId: String,
    val prependKey: String?,
    val appendKey: String?,
)