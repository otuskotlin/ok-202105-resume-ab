package ru.otus.otuskotlin.resume.service.services

import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.openapi.models.*

class ResumeServiceImpl {
    fun createResume(context: ResumeContext, request: CreateResumeRequest): CreateResumeResponse {
        context.setQuery(request)
        context.responseResume = Ivan.getModel()
        return context.toCreateResponse()
    }

    fun readResume(context: ResumeContext, request: ReadResumeRequest): ReadResumeResponse {
        context.setQuery(request)
        context.responseResume = Ivan.getModel()
        return context.toReadResponse()
    }

    fun updateResume(context: ResumeContext, request: UpdateResumeRequest): UpdateResumeResponse {
        context.setQuery(request)
        context.responseResume = Ivan.getModel()
        return context.toUpdateResponse()
    }

    fun deleteResume(context: ResumeContext, request: DeleteResumeRequest): DeleteResumeResponse {
        context.setQuery(request)
        context.responseResume = Ivan.getModel()
        return context.toDeleteResponse()
    }

    fun error(context: ResumeContext, e: Throwable): BaseMessage {
        context.addError {
            from(e)
        }
        return context.toReadResponse()
    }
}