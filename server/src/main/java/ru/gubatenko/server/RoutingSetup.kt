package ru.gubatenko.server

import io.ktor.server.routing.Routing

abstract class RoutingSetup {
    abstract fun setupRouting(routing: Routing)
}