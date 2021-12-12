package ru.otus.otuskotln.resume.ktor.controllers

import io.ktor.http.cio.websocket.*
import io.ktor.server.testing.*
import org.junit.Test
import com.fasterxml.jackson.module.kotlin.readValue
import ru.otus.otuskotlin.resume.ktor.module
import ru.otus.otuskotlin.resume.openapi.models.BaseMessage
import ru.otus.otuskotlin.resume.openapi.models.CreateResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.CreateResumeResponse
import ru.otus.otuskotlin.resume.openapi.models.InitResumeResponse
import ru.otus.otuskotln.resume.ktor.Utils
import kotlin.test.assertIs

class WsControllerTest {
    @Test
    fun test() {
        withTestApplication({ module() }) {
//            handleWebSocketConversation("/ws") { incoming, outgoing ->
//                run {
//                    val responseFrame = incoming.receive()
//                    val response = Utils.mapper.readValue<BaseMessage>(responseFrame.readBytes())
//                    assertIs<InitResumeResponse>(response)
//                }

//                run {
//                    val request = CreateResumeRequest(
//                        createResume = Utils.stubCreatableResume,
//                        debug = Utils.stubDebugSuccess
//                    )
//                    val requestFrame = Frame.Text(Utils.mapper.writeValueAsString(request))
//                    outgoing.send(requestFrame)
//
//                    val responseFrame = incoming.receive()
//                    val response = Utils.mapper.readValue<BaseMessage>(responseFrame.readBytes())
//                    assertIs<CreateResumeResponse>(response)
//                }
//            }
        }
    }
}