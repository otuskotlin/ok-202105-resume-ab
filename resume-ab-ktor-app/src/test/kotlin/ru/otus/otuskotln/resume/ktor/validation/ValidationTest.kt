package ru.otus.otuskotln.resume.ktor.validation

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.module
import ru.otus.otuskotlin.resume.openapi.models.CreateResumeResponse
import ru.otus.otuskotln.resume.ktor.Utils
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationTest {
    @Test
    fun badJson() {
        withTestApplication({
            module(config = AppKtorConfig())
        }) {
            handleRequest(HttpMethod.Post, "/resume/create") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                setBody("{")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println("|$jsonString|")

                val res = Utils.mapper.readValue(response.content, CreateResumeResponse::class.java)
                    ?: fail("Incorrect response format")

                assertEquals(CreateResumeResponse.Result.ERROR, res.result)
                assertTrue {
                    res.errors?.find { it.message?.lowercase()?.contains("unexpected end-of-input") == true } != null
                }

            }
        }
    }
}