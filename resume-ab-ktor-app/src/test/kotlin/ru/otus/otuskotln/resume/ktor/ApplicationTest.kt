package ru.otus.otuskotln.resume.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.resume.ktor.module
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun rootTest() {
         withTestApplication(Application::module) {
             handleRequest(HttpMethod.Get, "/").apply {
                 assertEquals(HttpStatusCode.OK, response.status())
                 assertEquals("Hello World", response.content)
             }
         }
    }
}