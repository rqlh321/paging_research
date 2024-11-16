package ru.gubatenko.credential.store

import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.RefreshToken

abstract class CredentialSaveUseCase :
    suspend (CredentialSaveUseCase.Args) -> CredentialSaveUseCase.Result {
    sealed class Args {
        data class Default(
            val accessToken: AccessToken,
            val refreshToken: RefreshToken
        ) : Args()
    }

    sealed class Result {
        data object Success : Result()
        data object Fail : Result()
    }
}