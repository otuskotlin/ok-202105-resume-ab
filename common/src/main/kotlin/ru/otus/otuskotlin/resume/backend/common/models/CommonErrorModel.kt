package ru.otus.otuskotlin.resume.backend.common.models


data class CommonErrorModel(
    override val field: String = "",
    override val level: IError.Level = IError.Level.ERROR,
    override val message: String = "",
) : IError
