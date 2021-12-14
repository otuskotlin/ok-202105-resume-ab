package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.cor.handlers.worker
import ru.otus.otuskotlin.resume.logics.chains.helpers.resumeValidation
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeUpdateStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.validation.validators.ValidatorStringNonEmpty

object ResumeUpdate : ICorExec<ResumeContext> by chain<ResumeContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.UPDATE,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    resumeUpdateStub(title = "Обработка стабкейса для UPDATE")
    resumeValidation {
        validate<String?> {
            on { this.requestResume.id.asString() }
            validator(ValidatorStringNonEmpty())
        }
    }
    chainPermissions("Вычисление разрешений для пользователя")
    worker(title = "инициализируем requestResumeId") { requestResumeId = requestResume.id }
    repoRead(title = "Чтение объекта из БД")
    accessValidation("Вычисление прав доступа")
    prepareResumeForSaving("Подготовка объекта для сохранения")
    repoUpdate(title = "Обновление данных объекта в БД")
    answerPrepareChain(title = "Подготовка ответа")
}).build()