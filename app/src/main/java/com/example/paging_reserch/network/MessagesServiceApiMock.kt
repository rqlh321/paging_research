package com.example.paging_reserch.network

import kotlinx.coroutines.delay

class MessagesServiceApiMock : MessagesServiceApi {

    override suspend fun messages(
        borderPosition: Long,
        isDirectionToLatest: Boolean,
        limit: Int,
    ) = generate(borderPosition, isDirectionToLatest, limit)

    private suspend fun generate(
        borderId: Long,
        isDirectionToLatest: Boolean,
        limit: Int,
    ): List<MessageNetworkDto> {
        delay(3000)
        val buildList = buildList {
            repeat(limit) {
                val delta = it * 1000 * 60 * 60
                val timestamp = if (isDirectionToLatest) borderId - delta else borderId + delta
                add(MessageNetworkDto(timestamp))
            }
        }
        return buildList
    }
}