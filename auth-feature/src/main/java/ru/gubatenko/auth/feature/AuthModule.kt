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
import ru.gubatenko.auth.feature.create.CreateAccountViewModel
import ru.gubatenko.auth.feature.login.LoginViewModel
import ru.gubatenko.credential.store.TokenStore
import ru.gubatenko.domain.auth.CreateAccountUseCase
import ru.gubatenko.domain.auth.IsCreateAccountAvailableUseCase
import ru.gubatenko.domain.auth.IsLoginAvailableUseCase
import ru.gubatenko.domain.auth.LoginUseCase
import ru.gubatenko.domain.auth.impl.AuthApi
import ru.gubatenko.user.domain.impl.CreateAccountUseCaseImpl
import ru.gubatenko.user.domain.impl.IsCreateAccountAvailableUseCaseImpl
import ru.gubatenko.domain.auth.impl.IsLoginAvailableUseCaseImpl
import ru.gubatenko.domain.auth.impl.LoginUseCaseImpl
import org.koin.core.module.dsl.bind

val authModule = module {
    viewModelOf(::AuthRootViewModel)

    scope<AuthRootViewModel> {
        scoped(named<AuthRout>()) { Channel<AuthRout>() }
        scopedOf(::AuthApi)

        scopedOf(::IsLoginAvailableUseCaseImpl) { bind<IsLoginAvailableUseCase>() }
        scopedOf(::LoginUseCaseImpl) { bind<LoginUseCase>() }
        viewModel {
            LoginViewModel(
                savedStateHandle = get(),
                routerRoot = get(),
                routerAuth = get(named<AuthRout>()),
                loginUseCase = get(),
                isLoginAvailableUseCase = get(),
            )
        }

        scopedOf(::IsCreateAccountAvailableUseCaseImpl) { bind<IsCreateAccountAvailableUseCase>() }
        scopedOf(::CreateAccountUseCaseImpl) { bind<CreateAccountUseCase>() }
        viewModel {
            CreateAccountViewModel(
                routerAuth = get(named<AuthRout>()),
                createAccountUseCase = get(),
                isCreateAccountAvailableUseCase = get(),
            )
        }
    }
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }
}