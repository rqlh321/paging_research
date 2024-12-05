package ru.gubatenko.domain.auth.impl

import io.ktor.http.HttpStatusCode

class BadRequestResponseStatus(val status: HttpStatusCode) : Exception()
