package ru.otus.otuskotlin.resume.ktor

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.slf4jLogger
import ru.otus.otuskotlin.resume.ktor.controller.createResume
import ru.otus.otuskotlin.resume.ktor.controller.deleteResume
import ru.otus.otuskotlin.resume.ktor.controller.readResume
import ru.otus.otuskotlin.resume.ktor.controller.updateResume
import ru.otus.otuskotlin.resume.ktor.modules.resume
import ru.otus.otuskotlin.resume.ktor.service.ResumeService
import ru.otus.otuskotlin.resume.ktor.service.ResumeServiceImpl

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("UNUSED_PARAMETER")
@JvmOverloads
fun Application.module() {
    val resumeService by inject<ResumeService>()

    install(DefaultHeaders)
    install(CallLogging)
    install(AutoHeadResponse)
    install(Koin) {
        modules(resume)
    }
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