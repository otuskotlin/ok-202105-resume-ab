package ru.otus.otuskotlin.validation

data class ValidationDefaultError(
    override val message: String,
) : IValidationError
