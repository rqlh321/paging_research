package com.example.paging_reserch.screen.auth

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.paging_reserch.App
import com.example.paging_reserch.screen.main.MainScreenDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(app) {

    var loginInProgress by mutableStateOf(false)
        private set
    var username by mutableStateOf(TextFieldValue())
        private set
    var usernameInputEnabled by mutableStateOf(true)
        private set
    var password by mutableStateOf(TextFieldValue())
        private set
    var passwordInputEnabled by mutableStateOf(true)
        private set
    val isLoginEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { loginInProgress },
        snapshotFlow { username }.mapLatest { it.text },
        snapshotFlow { password }.mapLatest { it.text },
    ) { loginInProgress, username, password ->
        !loginInProgress && username.isNotBlank() && password.isNotBlank()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

//    val isLoginEnabled by derivedStateOf {
//        username.text.isNotBlank() && password.text.isNotBlank()
//    }

    fun changeLogin(value: TextFieldValue) {
        username = value
    }

    fun changePassword(value: TextFieldValue) {
        password = value
    }

    fun onLoginClick() {
        usernameInputEnabled = false
        passwordInputEnabled = false
        loginInProgress = true
    }

    fun navigateToMain() {
        viewModelScope.launch {
            App.router.send(MainScreenDestination)
        }
    }
}