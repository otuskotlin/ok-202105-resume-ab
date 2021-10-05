package ru.otus.otuskotlin.resume.backend.common.context

import ru.otus.otuskotlin.resume.backend.common.models.*
import java.time.Instant

data class ResumeContext(
    var startTime: Instant = Instant.MIN,
    var operation: ResumeOperations = ResumeOperations.NONE,
    var stubCase: ResumeStubCase = ResumeStubCase.NONE,

    var onRequest: String = "",
    var requestResumeId: ResumeIdModel = ResumeIdModel.NONE,
    var requestResume: ResumeModel = ResumeModel(),
    var responseResume: ResumeModel = ResumeModel(),
    var errors: MutableList<IError> = mutableListOf(),
    var status: CorStatus = CorStatus.NONE
) {
    enum class ResumeOperations {
        NONE,
        INIT,
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

    /**
     * Добавляет ошибку в контекст
     *
     * @param error Ошибка, которую необходимо добавить в контекст
     * @param failingStatus Необходимо ли установить статус выполнения в FAILING (true/false)
     */
    fun addError(error: IError, failingStatus: Boolean = true) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }


    fun addError(
        e: Throwable,
        level: IError.Level = IError.Level.ERROR,
        field: String = "",
        failingStatus: Boolean = true
    ) {
        addError(CommonErrorModel(e, field = field, level = level), failingStatus)
    }
}