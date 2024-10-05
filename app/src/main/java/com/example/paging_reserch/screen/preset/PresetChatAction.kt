package com.example.paging_reserch.screen.preset

import com.example.paging_reserch.screen.Destination

sealed class PresetChatAction {
    data class NavigateTo(val destination: Destination) : PresetChatAction()
}