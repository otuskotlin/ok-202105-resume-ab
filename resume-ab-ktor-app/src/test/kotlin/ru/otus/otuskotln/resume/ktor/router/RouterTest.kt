package ru.otus.otuskotln.resume.ktor.router

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.resume.ktor.module
import ru.otus.otuskotlin.resume.openapi.models.BaseMessage
import ru.otus.otuskotln.resume.ktor.Utils
import kotlin.test.assertEquals

abstract class RouterTest  {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        crossinline block: T.() -> Unit
    ) {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                setBody(Utils.mapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())

                Utils.mapper.readValue(response.content, T::class.java).block()
            }
        }
    }
}