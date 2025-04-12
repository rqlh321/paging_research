package ru.gubatenko.auth.remote.api

import ru.gubatenko.auth.data.Credentials
import ru.gubatenko.auth.data.LoginBody

interface AuthApi {
    suspend fun login(body: LoginBody): Credentials
    suspend fun logout()
}