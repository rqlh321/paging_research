package com.example.paging_reserch.network

interface MessagesServiceApi {
    suspend fun messages(
        borderPosition: Long,
        isDirectionToLatest: Boolean,
        limit: Int,
    ): List<MessageNetworkDto>
}