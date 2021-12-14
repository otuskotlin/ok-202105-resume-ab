package ru.otus.otuskotlin.resume.ktor.controller

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.ktor.mappers.toModel
import ru.otus.otuskotlin.resume.logging.resumeLogger
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Instant

suspend fun ApplicationCall.initResume(resumeService: ResumeService) = handleRequest<InitResumeRequest, InitResumeResponse> (
    "init-resume",
    resumeLogger(this::class.java),
    { request -> resumeService.initResume(this, request)},
    { e -> resumeService.error(this, e) as InitResumeResponse}
)

suspend fun ApplicationCall.createResume(resumeService: ResumeService) = handleRequest<CreateResumeRequest, CreateResumeResponse> (
    "create-resume",
    resumeLogger(this::class.java),
    { request -> resumeService.createResume(this, request)},
    { e -> resumeService.error(this, e) as CreateResumeResponse}
)

suspend fun ApplicationCall.readResume(resumeService: ResumeService) = handleRequest<ReadResumeRequest, ReadResumeResponse> (
    "read-resume",
    resumeLogger(this::class.java),
    { request -> resumeService.readResume(this, request)},
    { e -> resumeService.error(this, e) as ReadResumeResponse}
)

suspend fun ApplicationCall.updateResume(resumeService: ResumeService) = handleRequest<UpdateResumeRequest, UpdateResumeResponse> (
    "update-resume",
    resumeLogger(this::class.java),
    { request -> resumeService.updateResume(this, request)},
    { e -> resumeService.error(this, e) as UpdateResumeResponse}
)

suspend fun ApplicationCall.deleteResume(resumeService: ResumeService) = handleRequest<DeleteResumeRequest, DeleteResumeResponse> (
    "delete-resume",
    resumeLogger(this::class.java),
    { request -> resumeService.deleteResume(this, request)},
    { e -> resumeService.error(this, e) as DeleteResumeResponse}
)