package com.example.paging_reserch.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import ru.gubatenko.app.navigation.Destination
import ru.gubatenko.app.navigation.auth.AuthGraph
import ru.gubatenko.auth.feature.AuthScreen
import ru.gubatenko.app.navigation.auth.AuthScreenDestination
import ru.gubatenko.app.navigation.auth.CreateAccountScreenDestination
import ru.gubatenko.app.navigation.auth.RestoreAccountScreenDestination
import com.example.paging_reserch.screen.chat.ChatScreen
import ru.gubatenko.app.navigation.ChatScreenDestination
import ru.gubatenko.app.navigation.MainScreenDestination
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootScreen() {
    val viewModel = koinViewModel<RootScreenViewModel>()
    val state by viewModel.state.collectAsState()
    val startDestination = when (state) {
        is RootScreenViewModel.RootScreenState.Authorized -> AuthGraph

        is RootScreenViewModel.RootScreenState.NotAuthorized -> MainScreenDestination

        is RootScreenViewModel.RootScreenState.Undefined -> return
    }
    RootContent(startDestination, viewModel)
}

@Composable
private fun RootContent(
    startDestination: Destination,
    viewModel: RootScreenViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.routing.collectLatest { navController.navigate(it) }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<AuthGraph>(
            startDestination = AuthScreenDestination
        ) {
            composable<AuthScreenDestination> { AuthScreen() }
            composable<CreateAccountScreenDestination> { }
            composable<RestoreAccountScreenDestination> { }
        }

        composable<ChatScreenDestination> { ChatScreen() }
        composable<MainScreenDestination> { }
    }
}