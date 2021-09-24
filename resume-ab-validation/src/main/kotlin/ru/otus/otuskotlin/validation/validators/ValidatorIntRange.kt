package ru.otus.otuskotlin.validation.validators

import ru.otus.otuskotlin.validation.IValidator
import ru.otus.otuskotlin.validation.ValidationFieldError
import ru.otus.otuskotlin.validation.ValidationResult

class ValidatorIntRange<T : Comparable<T>>(
    private val field: String,
    private val min: T,
    private val max: T,
) : IValidator<T> {
    override fun validate(sample: T): ValidationResult = if (sample in min..max) {
        ValidationResult.SUCCESS
    } else {
        ValidationResult(
            errors = listOf(
                ValidationFieldError(
                    message = "Value $sample for field $field exceeds range [$min, $max]",
                    field = field
                )
            )
        )
    }
}