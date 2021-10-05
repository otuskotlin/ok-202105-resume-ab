package ru.otus.otuskotlin.validation.cor

interface IValidationOperation<C, T> {
    fun exec(context: C)
}