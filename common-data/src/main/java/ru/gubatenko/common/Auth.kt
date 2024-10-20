package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource(ApiRouts.AUTH)
data object AuthRout

@Serializable
data class AuthBody(val username: String)
