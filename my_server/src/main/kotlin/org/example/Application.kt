package org.example

import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureHTTP()
    configureSerialization()
    configureStatusPages()
    configureRouting()
}
