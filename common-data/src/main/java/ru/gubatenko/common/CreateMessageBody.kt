package ru.gubatenko.common

import kotlinx.serialization.*

@Serializable
data class CreateMessageBody(
    val chatId: ChatId,
    val text: String
)