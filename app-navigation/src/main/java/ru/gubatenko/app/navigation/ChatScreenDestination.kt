package ru.gubatenko.app.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ChatScreenDestination(
    val id: String
) : Destination()

