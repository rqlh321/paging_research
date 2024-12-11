package ru.gubatenko.auth.feature

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.scope.ScopeViewModel
import org.koin.core.qualifier.named
import ru.gubatenko.app.navigation.AuthRout

class AuthRootViewModel : ScopeViewModel() {
    private val mutableState = MutableStateFlow<AuthRout>(AuthRout.Login)
    val startDestination = mutableState.asStateFlow()

    private val channel by scope.inject<Channel<AuthRout>>(named<AuthRout>())
    val routing = channel.receiveAsFlow()

    init {
        println("Init AuthRootViewModel ${hashCode()}!")
    }

    override fun onCleared() {
        println("Clear AuthRootViewModel ${hashCode()}!")
        super.onCleared()
    }
}