package ru.gubatenko.auth.data

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.Password
import ru.gubatenko.common.RefreshToken
import ru.gubatenko.common.Response
import ru.gubatenko.common.Username

@Resource(ApiRouts.LOGIN)
data object LoginRout

@Resource(ApiRouts.CREATE)
data object CreateAccountRout

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