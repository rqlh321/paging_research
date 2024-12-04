package com.example.paging_reserch.screen.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging_reserch.screen.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.gubatenko.credential.store.TokenStore

class RootScreenViewModel(
    channel: Channel<Destination>,
    private val tokenStore: TokenStore,
) : ViewModel() {

    private val mutableState = MutableStateFlow<RootScreenState>(RootScreenState.Undefined)
    val state = mutableState.asStateFlow()
    val routing = channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (tokenStore.isAuthorized()) {
                mutableState.emit(RootScreenState.NotAuthorized)
            } else {
                mutableState.emit(RootScreenState.Authorized)
            }
        }
    }

    sealed class RootScreenState {
        data object Undefined : RootScreenState()
        data object Authorized : RootScreenState()
        data object NotAuthorized : RootScreenState()
    }
}