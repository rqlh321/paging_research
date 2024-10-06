package ru.gubatenko.common

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: ChatId,
    val name: String
)
