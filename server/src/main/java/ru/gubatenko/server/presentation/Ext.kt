package ru.gubatenko.server.presentation

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import ru.gubatenko.common.UserId
import ru.gubatenko.server.domain.LoginUseCase.Companion.claimUserId

fun ApplicationCall.userId() = principal<JWTPrincipal>()
    ?.payload
    ?.getClaim(claimUserId)
    ?.asString()
    ?.let(::UserId)
    ?: error("Claim user id missing")