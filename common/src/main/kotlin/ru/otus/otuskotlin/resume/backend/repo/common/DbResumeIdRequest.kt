package ru.otus.otuskotlin.resume.backend.repo.common

import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel

data class DbResumeIdRequest(
    val id: ResumeIdModel
): IDbRequest
