package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker

fun ICorChainDsl<ResumeContext>.prepareResumeForSaving(title: String) {
    worker {
        this.title = title
        description = description
        on { status == CorStatus.RUNNING }
        handle {
            with(dbResume) {
                lastName = requestResume.lastName
                firstName = requestResume.firstName
                middleName = requestResume.middleName
                age = requestResume.age
                birthDate = requestResume.birthDate
                gender = responseResume.gender
                visibility = requestResume.visibility
            }
        }
    }
}