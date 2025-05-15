package org.example.routes

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable
import org.example.InvalidNameException
import org.example.UnsupportedLanguageException
import java.time.LocalDateTime

@Serializable
data class GreetingResponse(
    val greeting: String,
    val timestamp: String,
    val details: GreetingDetails? = null,
)

@Serializable
data class GreetingDetails(
    val name_length: Int,
    val vowel_count: Int,
    val is_palindrome: Boolean,
)

fun Route.greetRoutes() {
    route("greet") {
        get {
            call.respond(
                GreetingResponse(
                    greeting = "Hello, World!",
                    timestamp = LocalDateTime.now().toString(),
                ),
            )
        }

        get("{name}") {
            val name = validateName(call.parameters["name"]!!)
            val lang = call.request.queryParameters["lang"] ?: "en"
            val details = call.request.queryParameters["details"]?.toBooleanStrictOrNull() ?: false

            val response =
                GreetingResponse(
                    greeting = generateGreeting(name, lang),
                    timestamp = LocalDateTime.now().toString(),
                    details = if (details) createDetails(name) else null,
                )

            call.respond(response)
        }
    }
}

private fun validateName(name: String): String {
    if (name.isBlank() || (!name.equals("Guest", true) && !name.matches(NAME_REGEX))) {
        throw InvalidNameException()
    }
    return name
}

private fun generateGreeting(
    name: String,
    lang: String,
): String {
    val greetings =
        SUPPORTED_LANGUAGES[lang.lowercase()]
            ?: throw UnsupportedLanguageException(lang)
    return "${greetings.random()}, $name!"
}

private fun createDetails(name: String) =
    GreetingDetails(
        name_length = name.length,
        vowel_count = countVowels(name),
        is_palindrome = isPalindrome(name),
    )

private fun countVowels(name: String) =
    name.lowercase()
        .count { it in VOWEL_CHARACTERS }

private fun isPalindrome(name: String): Boolean {
    val cleanName = name.lowercase().replace(WHITESPACE_REGEX, "")
    return cleanName == cleanName.reversed()
}

// Константы и регулярные выражения
private val NAME_REGEX = Regex("^[\\p{L}]+$")
private val WHITESPACE_REGEX = Regex("\\s+")
private val VOWEL_CHARACTERS = setOf('a', 'e', 'i', 'o', 'u', 'y', 'а', 'у', 'о', 'ы', 'и', 'э', 'я', 'ю', 'ё', 'е')

private val SUPPORTED_LANGUAGES =
    mapOf(
        "en" to listOf("Hello", "Hi", "Welcome"),
        "ru" to listOf("Привет", "Здравствуй", "Доброго времени суток"),
        "es" to listOf("Hola", "Buenos días", "¿Qué tal?"),
        "fr" to listOf("Bonjour", "Salut", "Coucou"),
    )
