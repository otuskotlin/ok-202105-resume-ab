package ru.otus.otuskotlin.resume.ktor

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import ru.otus.otuskotlin.resume.ktor.controller.createResume
import ru.otus.otuskotlin.resume.ktor.controller.deleteResume
import ru.otus.otuskotlin.resume.ktor.controller.readResume
import ru.otus.otuskotlin.resume.ktor.controller.updateResume
import ru.otus.otuskotlin.resume.service.services.ResumeService

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("UNUSED_PARAMETER")
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    val resumeService = ResumeService()

    install(DefaultHeaders)
    install(CallLogging)
    install(AutoHeadResponse)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            writerWithDefaultPrettyPrinter()
        }
    }
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

    install(Routing)

    routing {
        get("/") {
            call.respondText ("Hello World")
        }
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