package ru.gubatenko.common

import io.ktor.http.HttpStatusCode

class BadRequestResponseStatus(val status: HttpStatusCode) : Exception()
