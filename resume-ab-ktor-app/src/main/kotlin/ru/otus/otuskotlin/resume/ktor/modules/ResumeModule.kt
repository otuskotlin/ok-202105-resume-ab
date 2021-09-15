package ru.otus.otuskotlin.resume.ktor.modules

import org.koin.dsl.module
import ru.otus.otuskotlin.resume.ktor.service.ResumeService
import ru.otus.otuskotlin.resume.ktor.service.ResumeServiceImpl

val resume = module {
    single<ResumeService> { ResumeServiceImpl() }
}