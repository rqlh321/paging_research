package ru.gubatenko.user.profile.feature

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val userProfileModule = module {
    viewModelOf(::ProfileViewModel)
}