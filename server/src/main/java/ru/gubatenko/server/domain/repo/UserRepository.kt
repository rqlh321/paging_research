package ru.gubatenko.server.domain.repo

import ru.gubatenko.common.Password
import ru.gubatenko.common.Username

interface UserRepository {
    suspend fun isNotExist(username: Username):Boolean
    suspend fun create(username: Username, password: Password)
}