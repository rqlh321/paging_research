package ru.gubatenko.common

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource(ApiRouts.AUTH)
data object AuthRout

@Serializable
data class AuthBody(
    val username: Username,
    val password: Password
)

@Serializable
data class Credentials(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken
) : Response()