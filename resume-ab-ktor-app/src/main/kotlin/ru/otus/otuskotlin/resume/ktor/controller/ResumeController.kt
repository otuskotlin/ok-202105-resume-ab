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
    val request = receive<InitResumeRequest>()
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.INIT
    )
    val result = try {
        resumeService.initResume(context, request)
    } catch (e: Throwable) {
        resumeService.error(context, e) as InitResumeResponse
    }
    respond(result)
}

suspend fun ApplicationCall.createResume(resumeService: ResumeService) {
    val request = receive<CreateResumeRequest>()
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.CREATE
    )
    val result = try {
        resumeService.createResume(context, request)
    } catch (e: Throwable) {
        resumeService.error(context, e) as CreateResumeResponse
    }
    respond(result)
}

suspend fun ApplicationCall.readResume(resumeService: ResumeService) {
    val request = receive<ReadResumeRequest>()
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.READ
    )
    val result = try {
        resumeService.readResume(context, request)
    } catch (e: Throwable) {
        resumeService.error(context, e) as ReadResumeResponse
    }
    respond(result)
}

suspend fun ApplicationCall.updateResume(resumeService: ResumeService) {
    val request = receive<UpdateResumeRequest>()
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.UPDATE
    )
    val result = try {
        resumeService.updateResume(context,request)
    } catch (e: Throwable) {
        resumeService.error(context, e) as UpdateResumeResponse
    }
    respond(result)
}

suspend fun ApplicationCall.deleteResume(resumeService: ResumeService) {
    val request = receive<DeleteResumeRequest>()
    val context = ResumeContext(
        startTime = Instant.now(),
        operation = ResumeContext.ResumeOperations.DELETE
    )
    val result = try {
        resumeService.deleteResume(context,request)
    } catch (e: Throwable) {
        resumeService.error(context, e) as DeleteResumeResponse
    }
    respond(result)
}