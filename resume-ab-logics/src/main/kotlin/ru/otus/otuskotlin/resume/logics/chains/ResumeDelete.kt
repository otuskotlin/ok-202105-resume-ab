package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.logics.chains.helpers.resumeValidation
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeDeleteStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.validation.validators.ValidatorStringNonEmpty

object ResumeDelete : ICorExec<ResumeContext> by chain<ResumeContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.DELETE,
    )

    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    resumeDeleteStub(title = "Обработка стабкейса для DELETE")

    resumeValidation {
        validate<String?> {
            on { this.requestResumeId.asString() }
            validator(ValidatorStringNonEmpty(field = "requestResume.id"))
        }
    }
    chainPermissions("Вычисление разрешений для пользователя")
    repoRead(title = "Чтение объекта из БД")
    accessValidation("Вычисление прав доступа")
    repoDelete("Удаление объекта из БД")

    answerPrepareChain(title = "Подготовка ответа")
}).build()