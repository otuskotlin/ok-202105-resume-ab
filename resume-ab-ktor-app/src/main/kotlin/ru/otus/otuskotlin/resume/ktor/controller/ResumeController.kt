package ru.otus.otuskotlin.resume.ktor.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.ktor.service.ResumeService
import ru.otus.otuskotlin.resume.openapi.models.*

suspend fun ApplicationCall.initResume(resumeService: ResumeService) {
    val initResumeRequest = receive<InitResumeRequest>()
    respond(
        ResumeContext().setQuery(initResumeRequest).let {
            resumeService.readResume(it)
        }.toInitResponse()
    )
}

suspend fun ApplicationCall.createResume(resumeService: ResumeService) {
    val createResumeRequest = receive<CreateResumeRequest>()
    respond(
        ResumeContext().setQuery(createResumeRequest).let {
            resumeService.createResume(it)
        }.toCreateResponse()
    )
}

suspend fun ApplicationCall.readResume(resumeService: ResumeService) {
    val readResumeRequest = receive<ReadResumeRequest>()
    respond(
        ResumeContext().setQuery(readResumeRequest).let {
            resumeService.readResume(it)
        }.toReadResponse()
    )
}

suspend fun ApplicationCall.updateResume(resumeService: ResumeService) {
    val updateResumeRequest = receive<UpdateResumeRequest>()
    respond(
        ResumeContext().setQuery(updateResumeRequest).let {
            resumeService.updateResume(it)
        }.toUpdateResponse()
    )
}

suspend fun ApplicationCall.deleteResume(resumeService: ResumeService) {
    val deleteResumeRequest = receive<DeleteResumeRequest>()
    respond(
        ResumeContext().setQuery(deleteResumeRequest).let {
            resumeService.deleteResume(it)
        }.toDeleteResponse()
    )
}