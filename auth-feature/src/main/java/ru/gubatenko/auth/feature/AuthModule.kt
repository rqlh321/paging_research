package ru.gubatenko.auth.feature

import kotlinx.coroutines.channels.Channel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.gubatenko.app.navigation.AuthRout
import ru.gubatenko.auth.feature.create.CreateAccountViewModel
import ru.gubatenko.auth.feature.login.LoginViewModel

val authModule = module {
    viewModelOf(::AuthRootViewModel)

    scope<AuthRootViewModel> {
        scoped(named<AuthRout>()) { Channel<AuthRout>() }

        viewModel {
            LoginViewModel(
                savedStateHandle = get(),
                routerRoot = get(),
                routerAuth = get(named<AuthRout>()),
                loginUseCase = get(),
                isLoginAvailableUseCase = get(),
            )
        }
        viewModel {
            CreateAccountViewModel(
                routerAuth = get(named<AuthRout>()),
                createAccountUseCase = get(),
                isCreateAccountAvailableUseCase = get(),
            )
        }
    }
}