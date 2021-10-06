package ru.otus.otuskotlin.resume.ktor.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.withContext
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.IUserSession
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.toUpdateResponse
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Instant

class KtorUserSession(
    override val fwSession: WebSocketSession,
    private val objectMapper: ObjectMapper,
): IUserSession<WebSocketSession> {
    override suspend fun notifyResumeChanged(context: ResumeContext) {
        val event = context.toUpdateResponse()
        fwSession.send(Frame.Text(objectMapper.safeWriteValueAsString(event)))
    }
}

suspend fun ObjectMapper.safeWriteValueAsString(value: Any): String =
    withContext(Dispatchers.IO) {
        writeValueAsString(value)
    }

suspend fun WebSocketSession.handleSession(
    objectMapper: ObjectMapper,
    resumeService: ResumeService,
    userSessions: MutableSet<KtorUserSession>
) {
    val userSession = KtorUserSession(this, objectMapper)
    userSessions.add(userSession)

    try {
        run {
            serverRequest(InitResumeRequest(), userSession, resumeService)?.also {
                outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
            }
        }

        for(frame in incoming) {
            if(frame is Frame.Text) {
                val request = objectMapper.readValue<BaseMessage>(frame.readText())
                serverRequest(request, userSession, resumeService)?.also {
                    outgoing.send(Frame.Text(objectMapper.safeWriteValueAsString(it)))
                }
            }
        }
    } catch (_: ClosedReceiveChannelException){}
    finally {
        userSessions.remove(userSession)
    }
}

suspend fun serverRequest(request: BaseMessage?, userSession: KtorUserSession, resumeService: ResumeService): BaseMessage? {
    val context = ResumeContext(startTime = Instant.now(), userSession = userSession)
    return try {
        when (request) {
            is InitResumeRequest -> resumeService.initResume(context, request)
            is CreateResumeRequest -> resumeService.createResume(context, request)
            is ReadResumeRequest -> resumeService.readResume(context, request)
            is UpdateResumeRequest -> resumeService.updateResume(context, request)
            is DeleteResumeRequest -> resumeService.deleteResume(context, request)
            null -> {
                resumeService.finishResume(context)
                null
            }
            else -> throw UnsupportedOperationException("Unsupported request type")
        }
    } catch (e: Exception) {
        resumeService.error(context, e)
    }
}

fun ResumeService.finishResume(context: ResumeContext) {
    // TODO handle user disconnection
}