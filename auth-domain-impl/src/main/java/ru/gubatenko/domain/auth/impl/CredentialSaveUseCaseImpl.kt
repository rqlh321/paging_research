package ru.gubatenko.domain.auth.impl

import ru.gubatenko.common.AccessToken
import ru.gubatenko.common.RefreshToken
import ru.gubatenko.credential.store.CredentialSaveUseCase

class CredentialSaveUseCaseImpl: CredentialSaveUseCase() {
    override suspend fun invoke(p1: AccessToken, p2: RefreshToken) {
        TODO("Not yet implemented")
    }
}