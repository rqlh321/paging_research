package ru.gubatenko.server_api

data class ClientConfig(
    val host: String = "http://192.168.0.4",
    val port: Int = 8080,
    val connectTimeout: Long = 5000L,
    val requestTimeout: Long = 10000L,
    val wsPingTimeOut: Long = 15000L,
)