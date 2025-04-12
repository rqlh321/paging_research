package ru.gubatenko.common

import kotlinx.serialization.Serializable
import java.util.UUID

@JvmInline
@Serializable
value class MessageId(val value: String) {
    fun uuid(): UUID = UUID.fromString(value)
}
