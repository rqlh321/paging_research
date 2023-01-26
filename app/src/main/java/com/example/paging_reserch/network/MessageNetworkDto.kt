package com.example.paging_reserch.network

data class MessageNetworkDto(
    val id: Int,
    val text: String,
    val isWatched: Boolean = true,
) {
    constructor(id: Int) : this(id, id.toString())
}