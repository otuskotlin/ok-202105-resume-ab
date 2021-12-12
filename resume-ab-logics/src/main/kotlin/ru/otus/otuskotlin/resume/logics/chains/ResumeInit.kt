package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.logics.workers.answerPrepareChain
import ru.otus.otuskotlin.resume.logics.workers.chainInitWorker
import ru.otus.otuskotlin.resume.logics.workers.checkOperationWorker

object ResumeInit : ICorExec<ResumeContext> by chain<ResumeContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.INIT
    )
    chainInitWorker(title = "Инициализация чейна")
    answerPrepareChain(title = "Подготовка ответа")
}).build()