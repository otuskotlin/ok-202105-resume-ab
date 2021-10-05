package ru.otus.otuskotlin.resume.logics.chains.stubs

import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.exceptions.ResumeStubNotFound
import ru.otus.otuskotlin.resume.backend.common.models.ResumeStubCase
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.chain
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.resumeCreateStub(title: String) = chain{
    this.title = title
    on { status == CorStatus.RUNNING && stubCase != ResumeStubCase.NONE }

    worker {
        this.title = "CREATE:= Успешный стабкейс"
        on { stubCase == ResumeStubCase.SUCCESS }
        handle {
            responseResume = requestResume.copy(id = Ivan.getModel().id)
            status = CorStatus.FINISHING
        }
    }

    worker {
        this.title = "CREATE:= Обработка отсутствия подходящего стабкейса"
        on { status == CorStatus.RUNNING }
        handle {
            status = CorStatus.FAILING
            addError(
                e = ResumeStubNotFound(stubCase.name),
            )
        }
    }
}