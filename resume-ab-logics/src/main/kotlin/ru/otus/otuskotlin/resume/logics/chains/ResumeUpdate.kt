package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeUpdateStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.resume.logics.workers.checkOperationWorker

object ResumeUpdate : ICorExec<ResumeContext> by chain<ResumeContext> ({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.UPDATE,
    )

    chainInitWorker(title = "Инициализация чейна")

    // TODO: Валидация запроса

    resumeUpdateStub(title = "Обработка стабкейса для UPDATE")

    // TODO: продовая логика, работа с БД

    answerPrepareChain(title = "Подготовка ответа")
}).build()