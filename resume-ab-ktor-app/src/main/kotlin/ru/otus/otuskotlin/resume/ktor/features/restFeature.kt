package ru.otus.otuskotlin.resume.ktor.features

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.controller.createResume
import ru.otus.otuskotlin.resume.ktor.controller.deleteResume
import ru.otus.otuskotlin.resume.ktor.controller.readResume
import ru.otus.otuskotlin.resume.ktor.controller.updateResume

fun Application.restFeature(config: AppKtorConfig) {
    val resumeService = config.resumeService

    install(DefaultHeaders)
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost()
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            writerWithDefaultPrettyPrinter()
        }
    }

    install(AutoHeadResponse)

    routing {
        get("/") {
            call.respondText("Hello World")
        }
        authenticate("auth-jwt") {
            route("resume") {
                post("create") {
                    call.createResume(resumeService)
                }
                post("read") {
                    call.readResume(resumeService)
                }
                post("update") {
                    call.updateResume(resumeService)
                }
                post("delete") {
                    call.deleteResume(resumeService)
                }
                static("static") {
                    resources("static")
                }
            }
        }
    }
}