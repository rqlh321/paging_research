package com.example.paging_reserch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paging_reserch.screen.chat.ChatScreen
import com.example.paging_reserch.screen.preset.ChatPresetsScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "preset"
                ) {
                    composable("preset") { ChatPresetsScreen { navController.navigate("chat") } }
                    composable("chat") { ChatScreen() }
                }
            }
        }
    }
}