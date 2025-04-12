package ru.gubatenko.app.core.android

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.RefreshToken
import ru.gubatenko.credential.store.TokenStore

class TokenStoreImpl(
    context: Context
) : TokenStore() {
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        SECRET_FILE_NAME,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun update(
        accessToken: AccessToken,
        refreshToken: RefreshToken
    ) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken.value)
            .putString(REFRESH_TOKEN, refreshToken.value)
            .apply()
    }

    override fun getAccessToken() = AccessToken(
        sharedPreferences.getString(ACCESS_TOKEN, null).orEmpty()
    )

    override fun getRefreshToken() = RefreshToken(
        sharedPreferences.getString(REFRESH_TOKEN, null).orEmpty()
    )

    override fun isAuthorized() = sharedPreferences.contains(ACCESS_TOKEN) &&
            sharedPreferences.contains(REFRESH_TOKEN)

    companion object {
        private const val SECRET_FILE_NAME = "secret_shared_prefs"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}