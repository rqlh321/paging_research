package ru.gubatenko.app.navigation

import kotlinx.serialization.Serializable

sealed class RootRout {
    @Serializable
    data object MainScreenDestination : RootRout()

    @Serializable
    data class ChatScreenDestination(val id: String) : RootRout()

    @Serializable
    data object AuthGraph : RootRout()
}

sealed class AuthRout {
    @Serializable
    data object Login : AuthRout()

    @Serializable
    data object CreateAccount : AuthRout()

    @Serializable
    data object RestoreAccount : AuthRout()
}