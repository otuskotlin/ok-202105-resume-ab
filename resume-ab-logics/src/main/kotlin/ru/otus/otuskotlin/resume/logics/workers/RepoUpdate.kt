package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.repoUpdate(title: String) = worker {
    this.title = title
    description = "Data from request updates the DB Repository object"

    on { status == CorStatus.RUNNING }

    handle {
        val result = resumeRepo.update(DbResumeModelRequest(resume = requestResume))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseResume = resultValue
        } else {
            result.errors.forEach { addError(it) }
        }
    }
}