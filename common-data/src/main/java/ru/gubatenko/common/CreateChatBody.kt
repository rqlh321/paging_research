package ru.gubatenko.common

import kotlinx.serialization.*

@Serializable
data class CreateChatBody(val name: String)