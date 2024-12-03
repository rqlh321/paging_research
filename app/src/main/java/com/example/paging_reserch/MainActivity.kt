package com.example.paging_reserch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paging_reserch.screen.Destination
import com.example.paging_reserch.screen.root.RootScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = viewModel<RootScreenViewModel>()
            val state by viewModel.state.collectAsState()
            when (state) {
                is MainActivityState.Defined -> {
                    RootScreen((state as MainActivityState.Defined).startDestination)
                }

                is MainActivityState.Undefined -> Unit
            }
        }
    }
}

sealed class MainActivityState {
    data object Undefined : MainActivityState()
    data class Defined(val startDestination: Destination) : MainActivityState()
}