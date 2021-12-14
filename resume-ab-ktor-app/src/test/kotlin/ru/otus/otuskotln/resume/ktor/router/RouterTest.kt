package ru.otus.otuskotln.resume.ktor.router

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.configs.KtorAuthConfig
import ru.otus.otuskotlin.resume.ktor.module
import ru.otus.otuskotlin.resume.openapi.models.BaseMessage
import ru.otus.otuskotln.resume.ktor.Utils
import kotlin.test.assertEquals

abstract class RouterTest  {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        config: AppKtorConfig = AppKtorConfig(),
        result: HttpStatusCode = HttpStatusCode.OK,
        crossinline block: T.() -> Unit = {}
    ) {
        withTestApplication({
            module(config = config)
        }) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                addHeader(HttpHeaders.Authorization, "Bearer ${KtorAuthConfig.testUserToken()}")
                setBody(Utils.mapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                if( result == HttpStatusCode.OK) {
                    assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                    Utils.mapper.readValue(response.content, T::class.java).block()
                }
            }
        }
    }
}