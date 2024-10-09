package ru.gubatenko.common

import kotlinx.serialization.Serializable

@Serializable
data class CreateChat(
    val name:String
)