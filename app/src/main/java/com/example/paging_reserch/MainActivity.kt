package com.example.paging_reserch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paging_reserch.screen.auth.AuthScreen
import com.example.paging_reserch.screen.auth.AuthScreenDestination
import com.example.paging_reserch.screen.chat.ChatScreen
import com.example.paging_reserch.screen.chat.ChatScreenDestination
import com.example.paging_reserch.screen.main.MainScreenDestination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                App.router.receiveAsFlow().collectLatest { navController.navigate(it) }
            }

            NavHost(
                navController = navController,
                startDestination = AuthScreenDestination
            ) {
                composable<AuthScreenDestination> { AuthScreen() }
                composable<MainScreenDestination> { Box { } }
                composable<ChatScreenDestination> { ChatScreen() }
            }
        }
    }
}