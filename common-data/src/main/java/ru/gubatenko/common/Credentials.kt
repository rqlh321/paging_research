package ru.gubatenko.common

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class AccessToken(val value: String)

@JvmInline
@Serializable
value class RefreshToken(val value: String)