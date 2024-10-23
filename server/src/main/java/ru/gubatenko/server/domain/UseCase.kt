package ru.gubatenko.server.domain

import ru.gubatenko.common.Response

abstract class UseCase<T> {
    abstract suspend fun run(args: T): Response
}