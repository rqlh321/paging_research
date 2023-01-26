package com.example.paging_reserch.adapter

data class Message(
    val id: Int,
    val text: String,
    val isWatched: Boolean = true,
) {
    constructor(id: Int) : this(id, id.toString())
}