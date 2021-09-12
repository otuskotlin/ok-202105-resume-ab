package ru.otus.otuskotlin.resume.backend.common.context

import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.backend.common.models.IError
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel

data class ResumeContext(
    var onRequest: String = "",
    var requestResumeId: ResumeIdModel = ResumeIdModel.NONE,
    var requestResume: ResumeModel = ResumeModel(),
    var responseResume: ResumeModel = ResumeModel(),
    var errors: MutableList<IError> = mutableListOf(),
    var status: CorStatus = CorStatus.NONE
)

fun ResumeContext.addError(lambda: CommonErrorModel.() -> Unit) =
    apply {
        status = CorStatus.FAILING
        errors.add(
            CommonErrorModel(
                field = "_", level = IError.Level.ERROR
            ).apply(lambda)
        )
    }