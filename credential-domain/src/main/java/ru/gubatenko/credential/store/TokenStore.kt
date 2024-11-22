package ru.gubatenko.credential.store

import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.RefreshToken

abstract class TokenStore {
    abstract fun update(accessToken: AccessToken, refreshToken: RefreshToken)
    abstract fun getAccessToken(): AccessToken
    abstract fun getRefreshToken(): RefreshToken
}