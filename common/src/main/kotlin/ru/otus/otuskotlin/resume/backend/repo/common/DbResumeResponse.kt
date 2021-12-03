package ru.otus.otuskotlin.resume.backend.repo.common

import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel

data class DbResumeResponse(
    override val result: ResumeModel?,
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel>
): IDbResponse<ResumeModel?>