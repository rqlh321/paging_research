package ru.gubatenko.common

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: MessageId,
    val text: String
)
