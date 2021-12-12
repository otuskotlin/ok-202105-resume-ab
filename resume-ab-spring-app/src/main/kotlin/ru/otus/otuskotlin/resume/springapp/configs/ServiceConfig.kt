package ru.otus.otuskotlin.resume.springapp.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.resume.backend.common.context.ContextConfig
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.repo.inmemory.RepoResumeInMemory
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Duration

@Configuration
class ServiceConfig {
    @Bean
    fun resumeService(crud: ResumeCrud): ResumeService = ResumeService(crud)
    @Bean
    fun contextConfig(): ContextConfig = ContextConfig(
        repoProd = RepoResumeInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
        repoTest = RepoResumeInMemory(initObjects = listOf()),
    )
    @Bean
    fun crud(contextConfig: ContextConfig): ResumeCrud = ResumeCrud(contextConfig)

}