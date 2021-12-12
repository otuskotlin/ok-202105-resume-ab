package ru.otus.otuskotlin.resume.rabbit

import ru.otus.otuskotlin.resume.backend.common.context.ContextConfig
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.repo.inmemory.RepoResumeInMemory
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Duration

class RabbitConfig(
    val host: String = HOST,
    val port: Int = PORT,
    val user: String = USER,
    val password: String = PASSWORD,
    val contextConfig: ContextConfig = ContextConfig(
        repoProd = RepoResumeInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
        repoTest = RepoResumeInMemory(initObjects = listOf()),
    ),
    val crud: ResumeCrud = ResumeCrud(contextConfig),
    val service: ResumeService = ResumeService(crud),
) {
    companion object {
        const val HOST = "localhost"
        const val PORT = 5672
        const val USER = "guest"
        const val PASSWORD = "guest"
    }
}