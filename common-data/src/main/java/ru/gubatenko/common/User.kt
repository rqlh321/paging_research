package ru.gubatenko.common

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class UserId(val value: String)
