import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

// Логгер для приложения
val logger = LoggerFactory.getLogger("Application")

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }

        routing {
            get("/") {
                call.respondText("Привет из Kotlin API!")
            }

            get("/hello") {
                call.respond(mapOf("message" to "Hello from Kotlin!"))
            }

            post("/echo") {
                try {
                    val payload = call.receive<Message>()
                    logger.info("Received payload: $payload")
                    call.respond(mapOf("you_said" to payload.text))
                } catch (e: Exception) {
                    logger.error("Error receiving payload: ", e)
                    call.respond(mapOf("error" to "Invalid request"))
                }
            }
        }
    }.start(wait = true)
}

@Serializable
data class Message(val text: String)
