package ru.otus.otuskotlin.validation

data class ValidationFieldError(
    override val message: String,
    override val field: String
) : IValidationError, IValidationFieldError