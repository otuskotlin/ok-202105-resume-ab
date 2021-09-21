package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.exceptions.ResumeIllegalOperation
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.checkOperationWorker(
    targetOperation: ResumeContext.ResumeOperations,
    title: String
) = worker{
    this.title = title
    description = "Если в контексте недопустимая операция, то чейн неуспешен"
    on { operation != targetOperation }
    handle {
        status = CorStatus.FAILING
        addError(
            e = ResumeIllegalOperation("Expected ${targetOperation.name} but was ${operation.name}")
        )
    }
}
