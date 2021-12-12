package ru.otus.otuskotlin.resume.logics.chains.helpers

import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumePrincipalModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumePrincipalRelations
import ru.otus.otuskotlin.resume.backend.common.models.ResumeVisibilityModel

fun ResumeModel.resolveRelationsTo(principal: ResumePrincipalModel): Set<ResumePrincipalRelations> = listOf(
    ResumePrincipalRelations.NONE,
    ResumePrincipalRelations.OWN.takeIf { principal.id == ownerId },
    ResumePrincipalRelations.OWN.takeIf { visibility == ResumeVisibilityModel.PUBLIC },
    ResumePrincipalRelations.MODERATABLE.takeIf { visibility != ResumeVisibilityModel.OWNER_ONLY },
).filterNotNull().toSet()