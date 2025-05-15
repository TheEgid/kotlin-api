package org.example

import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.example.routes.greetRoutes
import org.example.routes.healthRoutes
import org.example.routes.helloRoutes

fun Application.configureRouting() {
    routing {
        route("api") {
            healthRoutes()
            helloRoutes()
            greetRoutes()
        }
    }
}
