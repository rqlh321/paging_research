package ru.gubatenko.app.navigation

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

data class PopUpData(
    val popUpTo: KClass<*>,
    val inclusive: Boolean
)

sealed class RootRout {
    open val popUpData: PopUpData? = null

    @Serializable
    data object MainScreenDestination : RootRout() {
        override val popUpData = PopUpData(AuthGraph::class, true)
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