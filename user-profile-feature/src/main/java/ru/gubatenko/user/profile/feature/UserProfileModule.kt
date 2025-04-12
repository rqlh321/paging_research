package ru.gubatenko.user.profile.feature

import android.system.Os.bind
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.gubatenko.domain.auth.LogoutUseCase
import ru.gubatenko.user.domain.DeleteAccountUseCase
import ru.gubatenko.user.domain.impl.DeleteAccountUseCaseImpl
import ru.gubatenko.domain.auth.impl.LogoutUseCaseImpl
import ru.gubatenko.user.domain.impl.UserApi

val userProfileModule = module {
    viewModelOf(::ProfileViewModel)

    singleOf(::UserApi)
    singleOf(::LogoutUseCaseImpl) { bind<LogoutUseCase>() }
    singleOf(::DeleteAccountUseCaseImpl) { bind<DeleteAccountUseCase>() }
}