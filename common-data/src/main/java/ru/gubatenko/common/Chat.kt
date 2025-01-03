package ru.gubatenko.common

import kotlinx.serialization.Serializable
import java.util.UUID

@JvmInline
@Serializable
value class ChatId(val value: String) {
    fun uuid(): UUID = UUID.fromString(value)
}