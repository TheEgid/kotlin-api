package org.example.routes

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class HelloResponse(
    val hello: String,
    val timestamp: String,
)

fun getHello(): HelloResponse {
    return HelloResponse(
        hello = "Hello Kotlin",
        timestamp = LocalDateTime.now().toString(),
    )
}

fun Route.helloRoutes() {
    val message: () -> HelloResponse = ::getHello

    get("hello") {
        call.respond(message())
    }
}
