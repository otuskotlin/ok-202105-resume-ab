package ru.otus.otuskotlin.resume.logics.chains.stubs

import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.exceptions.ResumeStubNotFound
import ru.otus.otuskotlin.resume.backend.common.models.ResumeStubCase
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.chain
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.resumeDeleteStub(title: String) = chain {
    this.title = "DELETE:= Обработка стабкейса"
    on { status == CorStatus.RUNNING && stubCase != ResumeStubCase.NONE }

    worker {
        this.title = "DELETE:= Успешный стабкейс"
        on { stubCase == ResumeStubCase.SUCCESS }
        handle {
            responseResume = Ivan.getModel { id = requestResumeId }
            status = CorStatus.FINISHING
        }
    }

    worker {
        this.title = "DELETE:= Обработка отсутствия подходящего стабкейса"
        on { status == CorStatus.RUNNING }
        handle {
            status = CorStatus.FAILING
            addError(
                e = ResumeStubNotFound(stubCase.name),
            )
        }
    }
}