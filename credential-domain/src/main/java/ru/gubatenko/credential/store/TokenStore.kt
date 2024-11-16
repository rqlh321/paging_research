package ru.gubatenko.credential.store

import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.RefreshToken

abstract class TokenStore {
    abstract fun getAccessToken(): AccessToken?
    abstract fun getRefreshToken(): RefreshToken?
}