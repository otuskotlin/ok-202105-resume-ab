package ru.otus.otuskotln.resume.ktor.repo

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.ktor.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.module
import ru.otus.otuskotlin.resume.openapi.models.BaseDebugRequest
import ru.otus.otuskotlin.resume.openapi.models.ReadResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.ReadResumeResponse
import ru.otus.otuskotlin.resume.openapi.models.ResumeVisibility
import ru.otus.otuskotlin.resume.repo.inmemory.RepoResumeInMemory
import ru.otus.otuskotln.resume.ktor.Utils
import kotlin.test.assertEquals
import kotlin.test.fail

class RepoReadTest {
    @Test
    fun readFromDb() {
        val resume = Ivan.getModel()

        withTestApplication ({
            val config = AppKtorConfig(
                resumeRepoTest = RepoResumeInMemory(initObjects = listOf(resume))
            )
            module(config)
        }) {
            handleRequest(HttpMethod.Post, "/resume/read") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                val request = ReadResumeRequest(
                    readResumeId = resume.id.asString(),
                    debug = BaseDebugRequest(mode = BaseDebugRequest.Mode.TEST)
                )
                val json = ObjectMapper().writeValueAsString(request)
                setBody(json)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println("|$jsonString|")

                val res = Utils.mapper.readValue(response.content, ReadResumeResponse::class.java)
                    ?: fail("Incorrect response format")

                assertEquals(ReadResumeResponse.Result.SUCCESS, res.result)
                assertEquals("123", res.readResume?.id)
                assertEquals(ResumeVisibility.PUBLIC, res.readResume?.visibility)
                assertEquals(resume.firstName, res.readResume?.firstName)
                assertEquals(resume.age, res.readResume?.age)
            }
        }
    }
}