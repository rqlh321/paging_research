package ru.gubatenko.common

import kotlinx.serialization.Serializable
import java.util.UUID

@JvmInline
@Serializable
value class UserId(val value: String) {
    fun uuid(): UUID = UUID.fromString(value)
}

@JvmInline
@Serializable
value class Username(val value: String)

@JvmInline
@Serializable
value class Password(val value: String)