package com.example.paging_reserch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging_reserch.screen.auth.AuthGraph
import com.example.paging_reserch.screen.main.MainScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RootScreenViewModel : ViewModel() {
    private val mutableState = MutableStateFlow<MainActivityState>(MainActivityState.Undefined)
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            if (App.tokenStore.isAuthorized()) {
                mutableState.emit(MainActivityState.Defined(MainScreenDestination))
            } else {
                mutableState.emit(MainActivityState.Defined(AuthGraph))
            }
        }
    }
}