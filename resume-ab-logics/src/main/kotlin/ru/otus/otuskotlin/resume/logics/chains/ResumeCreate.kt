package ru.otus.otuskotlin.resume.logics.chains

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.cor.ICorExec
import ru.otus.otuskotlin.resume.cor.chain
import ru.otus.otuskotlin.resume.cor.handlers.worker
import ru.otus.otuskotlin.resume.logics.chains.stubs.resumeCreateStub
import ru.otus.otuskotlin.resume.logics.workers.*
import ru.otus.otuskotlin.resume.logics.workers.checkOperationWorker
import ru.otus.otuskotlin.validation.cor.worker.validation
import ru.otus.otuskotlin.validation.validators.ValidatorStringNonEmpty

object ResumeCreate : ICorExec<ResumeContext> by chain<ResumeContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = ResumeContext.ResumeOperations.CREATE,
    )

    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    resumeCreateStub(title = "Обработка стабкейса для CREATE")

    validation {
        errorHandler { validationResult ->
            if (validationResult.isSuccess) return@errorHandler
            val errs = validationResult.errors.map {
                CommonErrorModel(message = it.message)
            }
            errors.addAll(errs)
            status = CorStatus.FAILING
        }

        validate<String?> {
            on {this.requestResume.id.asString()}
            validator(ValidatorStringNonEmpty())
        }
    }
    chainPermissions("Вычисление разрешений для пользователя")
        worker {
            title = "Инициализация dbResume"

            on { status == CorStatus.RUNNING}
            handle {
                dbResume.ownerId = principal.id
            }
        }
    accessValidation("Вычисление прав доступа")
    prepareResumeForSaving("Подготовка объекта для сохранения")
    repoCreate("Запись объекта в БД")
    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")
    answerPrepareChain(title = "Подготовка ответа")
}).build()