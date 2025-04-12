package ru.gubatenko.auth.data

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.gubatenko.common.ApiRouts
import ru.gubatenko.common.Password
import ru.gubatenko.common.Username

@Resource(ApiRouts.DELETE_USER)
data object DeleteUserRout

@Serializable
data object DeleteUserBody

@Resource(ApiRouts.CREATE)
data object CreateAccountRout

@Serializable
data class CreateAccountBody(
    val username: Username,
    val password: Password
)