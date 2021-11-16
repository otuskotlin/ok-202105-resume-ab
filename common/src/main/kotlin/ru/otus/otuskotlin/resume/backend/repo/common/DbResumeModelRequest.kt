package ru.otus.otuskotlin.resume.backend.repo.common

import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel

data class DbResumeModelRequest(
    val resume: ResumeModel
): IDbRequest