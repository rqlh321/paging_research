package ru.gubatenko.server.domain

abstract class UseCase<T, R> : suspend (T) -> R