package ru.otus.otuskotlin.resume.ktor.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Instant

suspend fun ApplicationCall.initResume(resumeService: ResumeService) {
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.INIT
    )
    val result = try {
        val request = receive<InitResumeRequest>()
        resumeService.initResume(context, request)
    } catch (e: Throwable) {
        context.addError(e)
        context.toInitResponse()
    }
    respond(result)
}

suspend fun ApplicationCall.createResume(resumeService: ResumeService) {
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.CREATE
    )
    val result = try {
        val request = receive<CreateResumeRequest>()
        resumeService.createResume(context, request)
    } catch (e: Throwable) {
        context.addError(e)
        context.toCreateResponse()
    }
    respond(result)
}

suspend fun ApplicationCall.readResume(resumeService: ResumeService) {
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.READ
    )
    val result = try {
        val request = receive<ReadResumeRequest>()
        resumeService.readResume(context, request)
    } catch (e: Throwable) {
        context.addError(e)
        context.toReadResponse()
    }
    respond(result)
}

suspend fun ApplicationCall.updateResume(resumeService: ResumeService) {

    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.UPDATE
    )
    val result = try {
        val request = receive<UpdateResumeRequest>()
        resumeService.updateResume(context, request)
    } catch (e: Throwable) {
        context.addError(e)
        context.toUpdateResponse()
    }
    respond(result)
}

suspend fun ApplicationCall.deleteResume(resumeService: ResumeService) {
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.DELETE
    )
    val result = try {
        val request = receive<DeleteResumeRequest>()
        resumeService.deleteResume(context, request)
    } catch (e: Throwable) {
        context.addError(e)
        context.toDeleteResponse()
    }
    respond(result)
}