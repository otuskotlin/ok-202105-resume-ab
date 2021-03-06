package ru.otus.otuskotlin.resume.ktor.features

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
import ru.otus.otuskotlin.resume.ktor.configs.KtorAuthConfig
import ru.otus.otuskotlin.resume.ktor.controller.createResume
import ru.otus.otuskotlin.resume.ktor.controller.deleteResume
import ru.otus.otuskotlin.resume.ktor.controller.readResume
import ru.otus.otuskotlin.resume.ktor.controller.updateResume
import java.util.*

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
        get("login") {
            // Check username and password
            // ...
            val token = JWT.create()
                .withAudience(KtorAuthConfig.TEST.audience)
                .withIssuer(KtorAuthConfig.TEST.issuer)
                .withClaim(KtorAuthConfig.GROUPS_CLAIM, mutableListOf("USER", "TEST"))
                .withClaim(KtorAuthConfig.F_NAME_CLAIM, "testFirstName")
                .withClaim(KtorAuthConfig.M_NAME_CLAIM, "testMiddleName")
                .withClaim(KtorAuthConfig.L_NAME_CLAIM, "testLastName")
                .withExpiresAt(Date(System.currentTimeMillis() + 6000000))
                .sign(Algorithm.HMAC256(KtorAuthConfig.TEST.secret))
            call.respond(hashMapOf("token" to token)).also {  println("Test JWT token got: $this") }
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