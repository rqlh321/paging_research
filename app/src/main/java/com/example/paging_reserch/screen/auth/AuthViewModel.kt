package com.example.paging_reserch.screen.auth

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.paging_reserch.App
import com.example.paging_reserch.screen.main.MainScreenDestination
import kotlinx.coroutines.delay
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

    var loginErrorMessage by mutableStateOf("")
        private set
    var isLoginInProgress by mutableStateOf(false)
        private set

    var username by mutableStateOf(TextFieldValue())
        private set
    var usernameInputEnabled by mutableStateOf(true)
        private set

    var password by mutableStateOf(TextFieldValue())
        private set
    var passwordInputEnabled by mutableStateOf(true)
        private set
    var isPasswordVisible by mutableStateOf(false)
        private set

    val isLoginEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { isLoginInProgress },
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

    fun changeLogin(value: TextFieldValue) {
        loginErrorMessage = ""
        username = value
    }

    fun changePassword(value: TextFieldValue) {
        loginErrorMessage = ""
        password = value
    }

    fun changePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onTextFieldFocused(state: FocusState) {
        if (state.isFocused) {
            loginErrorMessage = ""
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            isPasswordVisible = false
            usernameInputEnabled = false
            passwordInputEnabled = false
            isLoginInProgress = true
            delay(2000)
            loginErrorMessage = "Wrong e-mail or password"
            isLoginInProgress = false
            usernameInputEnabled = true
            passwordInputEnabled = true
            password = TextFieldValue()
        }

    }

    fun navigateToMain() {
        viewModelScope.launch {
            App.router.send(MainScreenDestination)
        }
    }
}