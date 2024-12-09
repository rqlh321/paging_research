package ru.gubatenko.auth.feature

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.gubatenko.credential.store.TokenStore
import ru.gubatenko.domain.auth.IsLoginAvailableUseCase
import ru.gubatenko.domain.auth.LoginUseCase
import ru.gubatenko.domain.auth.impl.AuthApi
import ru.gubatenko.domain.auth.impl.IsLoginAvailableUseCaseImpl
import ru.gubatenko.domain.auth.impl.LoginUseCaseImpl

val authModule = module {
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }

    singleOf(::AuthApi)
    singleOf(::IsLoginAvailableUseCaseImpl) { bind<IsLoginAvailableUseCase>() }
    singleOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
    viewModelOf(::AuthViewModel)
}