package ru.gubatenko.common

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class ChatId(private val value: String)

@JvmInline
@Serializable
value class MessageId(private val value: String)