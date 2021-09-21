package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.ICorExecDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.chainInitWorker(title: String) = worker {
    this.title = title
    description = "При старте обработки цепочки, статус еще не установлен. Проверяем его"

    on {
        status == CorStatus.NONE
    }
    handle {
        status = CorStatus.RUNNING
    }
}