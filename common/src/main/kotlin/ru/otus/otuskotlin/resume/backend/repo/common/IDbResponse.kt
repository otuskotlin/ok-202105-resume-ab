package ru.otus.otuskotlin.resume.backend.repo.common

import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel

interface IDbResponse<T> {
    val isSuccess: Boolean
    val errors: List<CommonErrorModel>
    val result: T
}
