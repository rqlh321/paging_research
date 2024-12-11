package ru.gubatenko.auth.feature

import kotlinx.coroutines.channels.Channel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.gubatenko.app.navigation.AuthRout
import ru.gubatenko.auth.feature.login.LoginViewModel
import ru.gubatenko.credential.store.TokenStore
import ru.gubatenko.domain.auth.IsLoginAvailableUseCase
import ru.gubatenko.domain.auth.LoginUseCase
import ru.gubatenko.domain.auth.impl.AuthApi
import ru.gubatenko.domain.auth.impl.IsLoginAvailableUseCaseImpl
import ru.gubatenko.domain.auth.impl.LoginUseCaseImpl

val authModule = module {
    viewModelOf(::AuthRootViewModel)

    scope<AuthRootViewModel> {
        scoped(named<AuthRout>()) { Channel<AuthRout>() }
        scopedOf(::AuthApi)
        scopedOf(::IsLoginAvailableUseCaseImpl) { bind<IsLoginAvailableUseCase>() }
        scopedOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
        viewModel {
            LoginViewModel(
                routerRoot = get(),
                routerAuth = get(named<AuthRout>()),
                loginUseCase = get(),
                isLoginAvailableUseCase = get(),
            )
        }
    }
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }
}