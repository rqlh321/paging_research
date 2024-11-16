package ru.gubatenko.server.domain.repo

import ru.gubatenko.auth.data.Credentials
import ru.gubatenko.common.Password
import ru.gubatenko.common.UserId
import ru.gubatenko.common.Username

interface CredentialRepository {

    suspend fun checkUserId(username: Username, password: Password): UserId?

    suspend fun isTokenExist(tokenId: String, userId: UserId): Boolean

    suspend fun createNewCredentials(userId: UserId): Credentials
}