package ru.gubatenko.auth.feature.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.scope.Scope

@Composable
fun LoginScreen(authScope: Scope) {
    val viewModel = koinViewModel<LoginViewModel>(scope = authScope)

    val isLoginEnabled by viewModel.isLoginEnabled.collectAsStateWithLifecycle()

    LoginScreenContent(
        loginErrorMessage = viewModel.loginErrorMessage,
        isLoginEnabled = isLoginEnabled,
        isLoginInProgress = viewModel.isLoginInProgress,
        usernameInput = viewModel.username,
        usernameInputEnabled = viewModel.usernameInputEnabled,
        passwordInput = viewModel.password,
        passwordInputEnabled = viewModel.passwordInputEnabled,
        isPasswordVisible = viewModel.isPasswordVisible,
        changeLogin = viewModel::changeLogin,
        changePassword = viewModel::changePassword,
        changePasswordVisibility = viewModel::changePasswordVisibility,
        onTextFieldFocused = viewModel::onTextFieldFocused,
        onLoginClick = viewModel::onLoginClick,
        onCreateAccountClick = viewModel::onCreateAccountClick,
    )
}

@Preview
@Composable
private fun LoginScreenContent(
    loginErrorMessage: String = "",
    isPasswordVisible: Boolean = false,
    isLoginEnabled: Boolean = false,
    isLoginInProgress: Boolean = false,
    usernameInputEnabled: Boolean = true,
    usernameInput: TextFieldValue = TextFieldValue(),
    passwordInputEnabled: Boolean = true,
    passwordInput: TextFieldValue = TextFieldValue(),
    changeLogin: (TextFieldValue) -> Unit = {},
    changePassword: (TextFieldValue) -> Unit = {},
    changePasswordVisibility: () -> Unit = {},
    onTextFieldFocused: (FocusState) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged(onTextFieldFocused),
                value = usernameInput,
                singleLine = true,
                enabled = usernameInputEnabled,
                onValueChange = changeLogin,
                label = { Text("Enter username") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .onFocusChanged(onTextFieldFocused),
                value = passwordInput,
                singleLine = true,
                enabled = passwordInputEnabled,
                onValueChange = changePassword,
                label = { Text("Enter password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = changePasswordVisibility
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                },
            )
            AnimatedVisibility(loginErrorMessage.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = loginErrorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(
                modifier = Modifier
                    .focusable()
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = onLoginClick,
                enabled = isLoginEnabled
            ) {
                Row {
                    val text = if (isLoginInProgress) "Login in progress..." else "Login"
                    Text(text)
                    if (isLoginInProgress) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(16.dp),
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                    }
                }

            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCreateAccountClick,
            ) {
                Text("Create account")
            }
        }
    }
}