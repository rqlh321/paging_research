package com.example.paging_reserch.screen.chat

import com.example.paging_reserch.screen.Destination
import kotlinx.serialization.Serializable

@Serializable
data class ChatScreenDestination(
    val id: String
) : Destination()

