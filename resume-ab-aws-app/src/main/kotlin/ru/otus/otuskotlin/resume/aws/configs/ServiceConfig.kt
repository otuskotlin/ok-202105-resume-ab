package ru.otus.otuskotlin.resume.aws.configs

import ru.otus.otuskotlin.resume.backend.common.context.ContextConfig
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.repo.inmemory.RepoResumeInMemory
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Duration

val contextConfig: ContextConfig = ContextConfig(
    repoProd = RepoResumeInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
    repoTest = RepoResumeInMemory(initObjects = listOf()),
)

val crud: ResumeCrud = ResumeCrud(contextConfig)
val resumeService = ResumeService(crud)