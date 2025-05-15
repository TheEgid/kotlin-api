package org.example.routes

import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.healthRoutes() {
    get("healthcheck") {
        call.respondText("ok", ContentType.Text.Plain)
    }
}
