package org.example

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.Level

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
    }

    install(CallLogging) {
        level = Level.INFO
        format(::logFormatter)
    }
}

private fun logFormatter(call: ApplicationCall): String {
    val method = call.request.httpMethod.value
    val uri = call.request.uri
    val status = call.response.status()?.value?.toString()?.padStart(3, '0') ?: "000"
    return "HTTP ${method.uppercase()} $uri → $status"
}
