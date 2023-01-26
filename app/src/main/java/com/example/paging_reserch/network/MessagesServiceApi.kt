package com.example.paging_reserch.network

interface MessagesServiceApi {
    suspend fun messages(
        borderPosition: Int? = null,
        isDirectionToLatest: Boolean = true,
        limit: Int,
    ): List<MessageNetworkDto>
}