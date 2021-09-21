package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeDeleteStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.resume.logics.workers.checkOperationWorker

object ResumeDelete : ICorExec<ResumeContext> by chain<ResumeContext> ({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.DELETE,
    )

    chainInitWorker(title = "Инициализация чейна")

    // TODO: Валидация запроса

    resumeDeleteStub(title = "Обработка стабкейса для DELETE")

    // TODO: продовая логика, работа с БД

    answerPrepareChain(title = "Подготовка ответа")
}).build()