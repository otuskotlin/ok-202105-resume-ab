package ru.otus.otuskotlin.resume.logics.chains.helpers

import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.validation.IValidationFieldError
import ru.otus.otuskotlin.validation.cor.ValidationBuilder
import ru.otus.otuskotlin.validation.cor.worker.validation

fun ICorChainDsl<ResumeContext>.resumeValidation(block: ValidationBuilder<ResumeContext>.() -> Unit) = validation {
    errorHandler { validationResult ->
        if (validationResult.isSuccess) return@errorHandler
        val errs = validationResult.errors.map {
            CommonErrorModel(
                message = it.message,
                field = if (it is IValidationFieldError) it.field else "",
            )
        }
        errors.addAll(errs)
        status = CorStatus.FAILING
    }
    block()
}
