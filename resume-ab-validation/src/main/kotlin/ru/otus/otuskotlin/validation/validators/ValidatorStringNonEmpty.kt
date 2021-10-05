package ru.otus.otuskotlin.validation.validators

import ru.otus.otuskotlin.validation.IValidator
import ru.otus.otuskotlin.validation.ValidationFieldError
import ru.otus.otuskotlin.validation.ValidationResult

class ValidatorStringNonEmpty(
    private val field: String = "",
    private val message: String = "String must not be empty"
): IValidator<String?> {
    override fun validate(sample: String?): ValidationResult {
        return if(sample.isNullOrEmpty()) {
            ValidationResult(
                errors = listOf(
                    ValidationFieldError(
                        field = field,
                        message = message,
                    )
                )
            )
        } else {
            ValidationResult.SUCCESS
        }
    }
}