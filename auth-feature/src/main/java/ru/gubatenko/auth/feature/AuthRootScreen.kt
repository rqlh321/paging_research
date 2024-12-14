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
import ru.gubatenko.auth.feature.create.CreateAccountScreen
import ru.gubatenko.auth.feature.login.LoginScreen

@Composable
fun AuthRootScreen() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<AuthRootViewModel>()
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.routing.collectLatest {
            when (it) {
                is AuthRout.GoBack -> navController.popBackStack()
                else -> navController.navigate(it)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<AuthRout.Login> { LoginScreen(viewModel.scope) }
        composable<AuthRout.CreateAccount> { CreateAccountScreen(viewModel.scope) }
    }
}