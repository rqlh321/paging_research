package com.example.paging_reserch.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paging_reserch.screen.chat.ChatScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import ru.gubatenko.app.navigation.RootRout
import ru.gubatenko.auth.feature.AuthRootScreen

@Composable
fun RootScreen() {
    val viewModel = koinViewModel<RootScreenViewModel>()
    val state by viewModel.state.collectAsState()
    val startDestination: RootRout = when (state) {
        is RootScreenViewModel.RootScreenState.Authorized -> RootRout.AuthGraph

        is RootScreenViewModel.RootScreenState.NotAuthorized -> RootRout.MainScreenDestination

        is RootScreenViewModel.RootScreenState.Undefined -> return
    }
    RootContent(startDestination, viewModel)
}

@Composable
private fun RootContent(
    startDestination: RootRout,
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
        composable<RootRout.AuthGraph> { AuthRootScreen() }
        composable<RootRout.ChatScreenDestination> { ChatScreen() }
        composable<RootRout.MainScreenDestination> { }
    }
}