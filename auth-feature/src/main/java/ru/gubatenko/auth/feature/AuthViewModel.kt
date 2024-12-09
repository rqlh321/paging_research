package ru.gubatenko.auth.feature

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.gubatenko.app.navigation.auth.CreateAccountScreenDestination
import ru.gubatenko.app.navigation.Destination
import ru.gubatenko.app.navigation.MainScreenDestination
import ru.gubatenko.app.navigation.auth.RestoreAccountScreenDestination
import ru.gubatenko.common.Password
import ru.gubatenko.common.Username
import ru.gubatenko.domain.auth.IsLoginAvailableUseCase
import ru.gubatenko.domain.auth.LoginUseCase

class AuthViewModel(
    savedStateHandle: SavedStateHandle,
    private val router: Channel<Destination>,
    private val loginUseCase: LoginUseCase,
    private val isLoginAvailableUseCase: IsLoginAvailableUseCase,
) : ViewModel() {

    private var loginJob: Job? = null

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
        snapshotFlow { username }.map { Username(it.text) },
        snapshotFlow { password }.map { Password(it.text) },
        IsLoginAvailableUseCase::Args
    ).mapLatest(isLoginAvailableUseCase)
        .map { it.isValid }
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
        loginJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                isPasswordVisible = false
                usernameInputEnabled = false
                passwordInputEnabled = false
                isLoginInProgress = true
                val args = LoginUseCase.Args.ByPassword(
                    username = Username(username.text),
                    password = Password(password.text),
                )
                when (loginUseCase(args)) {
                    is LoginUseCase.Result.Success -> router.send(MainScreenDestination)
                    is LoginUseCase.Result.ConnectionFail -> loginErrorMessage =
                        "Connection fail, check your internet connection"

                    is LoginUseCase.Result.WrongCredentials -> loginErrorMessage =
                        "Wrong login or password"
                }
            } finally {
                isLoginInProgress = false
                usernameInputEnabled = true
                passwordInputEnabled = true
                password = TextFieldValue()
            }
        }
    }

    fun onCreateAccountClick() {
        viewModelScope.launch {
            loginErrorMessage = ""
            router.send(CreateAccountScreenDestination)
            loginJob?.cancel()
        }
    }

    fun onRestoreAccountClick() {
        viewModelScope.launch {
            loginErrorMessage = ""
            router.send(RestoreAccountScreenDestination)
            loginJob?.cancel()
        }
    }

}