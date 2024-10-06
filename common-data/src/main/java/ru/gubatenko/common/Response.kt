package ru.gubatenko.common

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(val result: T)