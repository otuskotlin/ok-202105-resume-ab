package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeReadStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.resume.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.validation.cor.worker.validation
import ru.otus.otuskotlin.validation.validators.ValidatorStringNonEmpty

object ResumeRead : ICorExec<ResumeContext> by chain<ResumeContext> ({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.READ,
    )

    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    resumeReadStub(title = "Обработка стабкейса для READ")

    validation {
        validate<String?> {
            on {requestResumeId.asString()}
            validator(ValidatorStringNonEmpty(field = "id"))
        }
    }

    repoRead(title = "Чтение объекта из БД")

    answerPrepareChain(title = "Подготовка ответа")
}).build()