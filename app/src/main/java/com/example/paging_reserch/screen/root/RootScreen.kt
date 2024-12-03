package com.example.paging_reserch.screen.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.paging_reserch.App
import com.example.paging_reserch.screen.Destination
import com.example.paging_reserch.screen.auth.AuthGraph
import com.example.paging_reserch.screen.auth.AuthScreen
import com.example.paging_reserch.screen.auth.AuthScreenDestination
import com.example.paging_reserch.screen.auth.AuthViewModel
import com.example.paging_reserch.screen.auth.CreateAccountScreenDestination
import com.example.paging_reserch.screen.auth.RestoreAccountScreenDestination
import com.example.paging_reserch.screen.chat.ChatScreen
import com.example.paging_reserch.screen.chat.ChatScreenDestination
import com.example.paging_reserch.screen.main.MainScreenDestination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun RootScreen(startDestination: Destination) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        App.router.receiveAsFlow().collectLatest { navController.navigate(it) }
    }

    MaterialTheme {
        Surface {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                navigation<AuthGraph>(
                    startDestination = AuthScreenDestination
                ) {
                    composable<AuthScreenDestination> { AuthScreen(viewModel<AuthViewModel>()) }
                    composable<CreateAccountScreenDestination> { Surface(Modifier.fillMaxSize()) {} }
                    composable<RestoreAccountScreenDestination> { Surface(Modifier.fillMaxSize()) {} }
                }

                composable<ChatScreenDestination> { ChatScreen() }
                composable<MainScreenDestination> { Surface(Modifier.fillMaxSize()) {} }
            }
        }
    }
}