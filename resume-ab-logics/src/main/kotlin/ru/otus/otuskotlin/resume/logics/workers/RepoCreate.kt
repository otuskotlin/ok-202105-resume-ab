package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Data from request are stored in the DB Repository"
    on { status == CorStatus.RUNNING}

    handle {
        val result = resumeRepo.create(DbResumeModelRequest(resume = requestResume))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseResume = resultValue
        } else  {
            result.errors.forEach { addError(it) }
        }
    }
}