package ru.gubatenko.server

import io.ktor.server.application.Application
import ru.gubatenko.server.plugins.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
