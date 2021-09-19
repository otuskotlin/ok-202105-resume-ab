package ru.otus.otuskotlin.resume.springapp.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.resume.service.services.ResumeService

@Configuration
class ServiceConfig {
    @Bean
    fun resumeService(): ResumeService = ResumeService()
}