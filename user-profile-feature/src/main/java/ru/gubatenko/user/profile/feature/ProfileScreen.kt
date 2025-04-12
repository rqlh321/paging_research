package ru.gubatenko.user.profile.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen() {
    val viewModel = koinViewModel<ProfileViewModel>()

    Column {
        Button(viewModel::logout) { Text("Logout") }
        Button(viewModel::delete) { Text("Delete") }
    }
}

