package ru.gubatenko.user.remote.api

import ru.gubatenko.auth.data.CreateAccountBody

interface UserApi {
    suspend fun create(body: CreateAccountBody)
    suspend fun delete()
}