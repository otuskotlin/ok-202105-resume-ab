package ru.otus.otuskotlin.resume.service.services

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.service.services.exceptions.DataNotAllowedException
import org.slf4j.event.Level
import ru.otus.otuskotlin.resume.logging.resumeLogger

class ResumeService(
    private val crud: ResumeCrud,
) {
    private val logger = resumeLogger(this::class.java)

    suspend fun handleResume(context: ResumeContext, request: BaseMessage): BaseMessage = try {
        when (request) {
            is InitResumeRequest -> initResume(context, request)
            is CreateResumeRequest -> createResume(context, request)
            is ReadResumeRequest -> readResume(context, request)
            is UpdateResumeRequest -> updateResume(context, request)
            is DeleteResumeRequest -> deleteResume(context, request)
            else -> throw DataNotAllowedException("Request is not allowed", request)
        }
    } catch (e: Throwable) {
        error(context, e)
    }

    suspend fun initResume(context: ResumeContext, request: InitResumeRequest): InitResumeResponse {
        crud.init(context.setQuery(request))
        return context.handle("init-resume", ResumeContext::toInitResponse) {
            crud.init(it)
        }
    }

    suspend fun createResume(context: ResumeContext, request: CreateResumeRequest): CreateResumeResponse {
        crud.create(context.setQuery(request))
        return context.handle("create-resume", ResumeContext::toCreateResponse) {
            crud.create(it)
        }
    }

    suspend fun readResume(context: ResumeContext, request: ReadResumeRequest): ReadResumeResponse {
        crud.read(context.setQuery(request))
        return context.handle("read-resume", ResumeContext::toReadResponse) {
            crud.read(it)
        }
    }

    suspend fun updateResume(context: ResumeContext, request: UpdateResumeRequest): UpdateResumeResponse {
        crud.update(context.setQuery(request))
        return context.handle("update-resume", ResumeContext::toUpdateResponse) {
            crud.update(it)
        }
    }

    suspend fun deleteResume(context: ResumeContext, request: DeleteResumeRequest): DeleteResumeResponse {
        crud.delete(context.setQuery(request))
        return context.handle("delete-resume", ResumeContext::toDeleteResponse) {
            crud.delete(it)
        }
    }

    suspend fun error(context: ResumeContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.handle("error-resume", ResumeContext::toReadResponse)
    }

    private suspend fun <T> ResumeContext.handle(
        logId: String,
        mapper: ResumeContext.() -> T,
        block: suspend (ResumeContext) -> Unit = {}
    ): T {
        logger.log(
            msg = "Request got, query ={}",
            level = Level.INFO,
            data = toLog("$logId-request-got")
        )
        block(this)
        return mapper().also {logger.log(
            msg = "Response ready, response ={}",
            level = Level.INFO,
            data = toLog("$logId-request-handled")
        )}
    }
}