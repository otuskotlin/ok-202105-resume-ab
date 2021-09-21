package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeCreateStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.resume.logics.workers.checkOperationWorker

object ResumeCreate : ICorExec<ResumeContext> by chain<ResumeContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.CREATE,
    )

    chainInitWorker(title = "Инициализация чейна")
    // TODO: Валидация запроса

    resumeCreateStub(title = "Обработка стабкейса для CREATE")

    // TODO: продовая логика, работа с БД

    answerPrepareChain(title = "Подготовка ответа")
}).build()