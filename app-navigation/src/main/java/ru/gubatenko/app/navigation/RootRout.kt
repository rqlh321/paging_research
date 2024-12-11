package ru.gubatenko.app.navigation

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

sealed class RootRout {
    open val popUpTo: KClass<out RootRout>? = null

    @Serializable
    data object MainScreenDestination : RootRout(){
        override val popUpTo = AuthGraph::class
    }

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