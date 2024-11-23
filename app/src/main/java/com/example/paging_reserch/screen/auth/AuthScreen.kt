package com.example.paging_reserch.screen.auth

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthScreen() {
    val viewModel = viewModel<AuthViewModel>()
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsStateWithLifecycle()

    AuthScreenContent(
        isLoginEnabled = isLoginEnabled,
        usernameInput = viewModel.username,
        usernameInputEnabled = viewModel.usernameInputEnabled,
        passwordInput = viewModel.password,
        passwordInputEnabled = viewModel.passwordInputEnabled,
        changeLogin = viewModel::changeLogin,
        changePassword = viewModel::changePassword,
        onLoginClick = viewModel::onLoginClick
    )
}

@Preview
@Composable
private fun AuthScreenContent(
    isLoginEnabled: Boolean = false,
    usernameInputEnabled: Boolean = true,
    usernameInput: TextFieldValue = TextFieldValue(),
    passwordInputEnabled: Boolean = true,
    passwordInput: TextFieldValue = TextFieldValue(),
    changeLogin: (TextFieldValue) -> Unit = {},
    changePassword: (TextFieldValue) -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = usernameInput,
                singleLine = true,
                enabled = usernameInputEnabled,
                onValueChange = changeLogin,
                label = { Text("Enter email") },
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
                    .padding(top = 8.dp),
                value = passwordInput,
                singleLine = true,
                enabled = passwordInputEnabled,
                onValueChange = changePassword,
                label = { Text("Enter password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Button(
                modifier = Modifier
                    .focusable()
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = onLoginClick,
                enabled = isLoginEnabled
            ) { Text("Login") }

        }
    }
}