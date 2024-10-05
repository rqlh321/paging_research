package com.example.paging_reserch.screen.preset

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paging_reserch.screen.Destination
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun ChatPresetsScreen(
    navigateToDestination: (Destination) -> Unit = {}
) {
    val viewModel = viewModel<ChatPresetViewModel>()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.actions
            .onEach {
                when (it) {
                    is PresetChatAction.NavigateTo -> navigateToDestination(it.destination)
                }
            }
            .collect()
    }

    Column {
        state.buttons.forEach { ButtonComposable(it) }
    }
}