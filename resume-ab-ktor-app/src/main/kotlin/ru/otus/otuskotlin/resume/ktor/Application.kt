package ru.otus.otuskotlin.resume.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.features.authFeature
import ru.otus.otuskotlin.resume.ktor.features.restFeature
import ru.otus.otuskotlin.resume.ktor.features.wsFeature

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("UNUSED_PARAMETER")
@JvmOverloads
fun Application.module(config: AppKtorConfig = AppKtorConfig(environment)) {
    install(Routing)
    install(CallLogging)
    authFeature(config)
    restFeature(config)
    wsFeature(config)
}