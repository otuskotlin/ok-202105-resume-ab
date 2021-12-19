package ru.otus.otuskotlin.resume.ktor.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import ru.otus.otuskotlin.resume.backend.common.context.ContextConfig
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.ktor.controller.KtorUserSession
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.repo.inmemory.RepoResumeInMemory
import ru.otus.otuskotlin.resume.repo.sql.RepoResumeSQL
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Duration

data class AppKtorConfig(
    val userSessions: MutableSet<KtorUserSession> = mutableSetOf(),
    val objectMapper: ObjectMapper = jacksonObjectMapper(),
    val resumeRepoTest: IRepoResume = RepoResumeInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
    val resumeRepoProd: IRepoResume = RepoResumeSQL(initObjects = listOf()),
    val contextConfig: ContextConfig = ContextConfig(
        repoProd = resumeRepoProd,
        repoTest = resumeRepoTest
    ),
    val auth: KtorAuthConfig = KtorAuthConfig.TEST,
    val crud: ResumeCrud = ResumeCrud(contextConfig),
    val resumeService: ResumeService = ResumeService(crud)
) {
    constructor(environment: ApplicationEnvironment): this(
        auth = KtorAuthConfig(environment)
    )
    companion object{}}