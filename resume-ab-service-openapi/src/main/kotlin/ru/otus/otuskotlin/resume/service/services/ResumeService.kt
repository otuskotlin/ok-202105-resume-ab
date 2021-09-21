package ru.otus.otuskotlin.resume.service.services

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.openapi.models.*

class ResumeService(
    private val crud: ResumeCrud,
) {
    suspend fun initResume(context: ResumeContext, request: InitResumeRequest): InitResumeResponse {
        crud.init(context.setQuery(request))
        return context.toInitResponse()
    }

    suspend fun createResume(context: ResumeContext, request: CreateResumeRequest): CreateResumeResponse {
        crud.create(context.setQuery(request))
        return context.toCreateResponse()
    }

    suspend fun readResume(context: ResumeContext, request: ReadResumeRequest): ReadResumeResponse {
        crud.read(context.setQuery(request))
        return context.toReadResponse()
    }

    suspend fun updateResume(context: ResumeContext, request: UpdateResumeRequest): UpdateResumeResponse {
        crud.update(context.setQuery(request))
        return context.toUpdateResponse()
    }

    suspend fun deleteResume(context: ResumeContext, request: DeleteResumeRequest): DeleteResumeResponse {
        crud.delete(context.setQuery(request))
        return context.toDeleteResponse()
    }

    fun error(context: ResumeContext, e: Throwable): BaseMessage {
       context.addError(e)
        return context.toReadResponse()
    }
}