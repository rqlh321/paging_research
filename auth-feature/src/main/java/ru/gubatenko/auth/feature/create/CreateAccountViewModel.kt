package ru.gubatenko.auth.feature.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
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
import ru.gubatenko.app.navigation.AuthRout
import ru.gubatenko.common.Password
import ru.gubatenko.common.Username
import ru.gubatenko.domain.auth.CreateAccountUseCase
import ru.gubatenko.domain.auth.IsCreateAccountAvailableUseCase

class CreateAccountViewModel(
    private val routerAuth: Channel<AuthRout>,
    private val createAccountUseCase: CreateAccountUseCase,
    private val isCreateAccountAvailableUseCase: IsCreateAccountAvailableUseCase,
) : ViewModel() {

    private var createAccountJob: Job? = null

    var errorMessage by mutableStateOf("")
        private set
    var inProgress by mutableStateOf(false)
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

    val isCreateEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { inProgress },
        snapshotFlow { username }.map { Username(it.text) },
        snapshotFlow { password }.map { Password(it.text) },
        IsCreateAccountAvailableUseCase::Args
    ).mapLatest(isCreateAccountAvailableUseCase)
        .map { it.isValid }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun changeLogin(value: TextFieldValue) {
        errorMessage = ""
        username = value
    }

    fun changePassword(value: TextFieldValue) {
        errorMessage = ""
        password = value
    }

    fun changePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onTextFieldFocused(state: FocusState) {
        if (state.isFocused) {
            errorMessage = ""
        }
    }

    fun onCreateAccountClick() {
        createAccountJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                isPasswordVisible = false
                usernameInputEnabled = false
                passwordInputEnabled = false
                inProgress = true
                when (
                    createAccountUseCase(
                        CreateAccountUseCase.Args(
                            username = Username(username.text),
                            password = Password(password.text)
                        )
                    )
                ) {
                    CreateAccountUseCase.Result.ConnectionFail -> errorMessage =
                        "Connection fail, check your internet connection"

                    CreateAccountUseCase.Result.BadUsernameOrPasswordFail -> errorMessage =
                        "Bad username or password"

                    CreateAccountUseCase.Result.Success -> routerAuth.send(AuthRout.GoBack)
                }
            } finally {
                inProgress = false
                usernameInputEnabled = true
                passwordInputEnabled = true
                password = TextFieldValue()
            }
        }
    }

}