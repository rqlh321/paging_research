package ru.gubatenko.api.impl

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.gubatenko.auth.remote.api.AuthApi
import ru.gubatenko.user.remote.api.UserApi

val apiModule = module {
    singleOf(::AuthApiImpl) { bind<AuthApi>() }
    singleOf(::UserApiImpl) { bind<UserApi>() }
}