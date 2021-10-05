package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.chain
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.answerPrepareChain(title: String) = chain{
    this.title = title
    description = "Чейн считается успешным, если в нем не было ошибок и он отработал"
    worker {
        this.title = "Обработчик успешного чейна"
        on { status in setOf(CorStatus.RUNNING, CorStatus.FINISHING) }
        handle {
            status = CorStatus.SUCCESS
        }
    }
    worker {
        this.title = "Обработчик неуспешного чейна"
        on { status != CorStatus.SUCCESS }
        handle {
            status = CorStatus.ERROR
        }
    }
}