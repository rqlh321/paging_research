package ru.gubatenko.auth.feature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import ru.gubatenko.app.navigation.AuthRout
import ru.gubatenko.auth.feature.login.AuthScreen

@Composable
fun AuthRootScreen() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<AuthRootViewModel>()
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.routing.collectLatest { navController.navigate(it) }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<AuthRout.Login> { AuthScreen(viewModel.scope.get()) }
        composable<AuthRout.CreateAccount> { }
        composable<AuthRout.RestoreAccount> { }
    }
}