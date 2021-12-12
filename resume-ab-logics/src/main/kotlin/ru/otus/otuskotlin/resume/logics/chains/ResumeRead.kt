package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.cor.handlers.worker
import ru.otus.otuskotlin.resume.logics.chains.helpers.resumeValidation
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeReadStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.validation.validators.ValidatorStringNonEmpty

object ResumeRead : ICorExec<ResumeContext> by chain<ResumeContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.READ,
    )

    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    resumeReadStub(title = "Обработка стабкейса для READ")

    resumeValidation {
        validate<String?> {
            on { requestResumeId.asString() }
            validator(ValidatorStringNonEmpty(field = "id"))
        }
    }
    chainPermissions("Вычисление разрешений для пользователя")

    repoRead(title = "Чтение объекта из БД")
    accessValidation("Вычисление прав доступа")
    worker {
        title = "Подготовка результата к отправке"
        description = title
        on { status == CorStatus.RUNNING }
        handle { responseResume = dbResume }
    }
    frontPermissions(title = "Вычислений разрешений для фронтенда")

    answerPrepareChain(title = "Подготовка ответа")
}).build()