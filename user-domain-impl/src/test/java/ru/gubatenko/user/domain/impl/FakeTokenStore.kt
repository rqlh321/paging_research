package ru.gubatenko.user.domain.impl

import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.RefreshToken
import ru.gubatenko.credential.store.TokenStore

class FakeTokenStore : TokenStore() {
    override fun update(accessToken: AccessToken, refreshToken: RefreshToken) {
    }

    override fun getAccessToken() = AccessToken("")
    override fun getRefreshToken() = RefreshToken("")

    override fun isAuthorized() = false
}