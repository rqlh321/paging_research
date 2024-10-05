package com.example.paging_reserch.screen.chat

import com.example.paging_reserch.screen.Destination
import kotlinx.serialization.Serializable

@Serializable
data class ChatDestination(
    val id: String
) : Destination()

