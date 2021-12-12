package ru.otus.otuskotlin.resume.ktor.features

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.websocket.*
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.controller.handleSession

fun Application.wsFeature(config: AppKtorConfig) {
    val userSessions = config.userSessions
    val objectMapper = config.objectMapper
    val resumeService = config.resumeService

    install(WebSockets)
    routing {
        webSocket("ws") {
            this.handleSession(objectMapper, resumeService, userSessions)
        }
    }
}