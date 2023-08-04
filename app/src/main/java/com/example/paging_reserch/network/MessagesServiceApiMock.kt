package com.example.paging_reserch.network

import kotlinx.coroutines.delay

class MessagesServiceApiMock : MessagesServiceApi {

    override suspend fun messages(
        borderPosition: Int?,
        isDirectionToLatest: Boolean,
        limit: Int,
    ) = generate(borderPosition ?: 0, isDirectionToLatest, limit)

    private suspend fun generate(
        borderId: Int,
        isDirectionToLatest: Boolean,
        limit: Int,
    ): List<MessageNetworkDto> {
        delay(10000)
        val from = if (isDirectionToLatest) borderId + 1 else borderId - 1
        val to = if (isDirectionToLatest)
            borderId + limit else borderId - limit
        return (from..to).map(::MessageNetworkDto)
    }
}