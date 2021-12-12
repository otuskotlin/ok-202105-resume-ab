package ru.otus.otuskotlin.resume.logics.chains.helpers

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.ResumePrincipalRelations
import ru.otus.otuskotlin.resume.backend.common.models.ResumeUserPermissions

data class AccessTableConditions(
    val operation: ResumeContext.ResumeOperations,
    val permission: ResumeUserPermissions,
    val relation: ResumePrincipalRelations
)

val accessTable = mapOf(
    //Create
    AccessTableConditions(
        operation = ResumeContext.ResumeOperations.CREATE,
        permission = ResumeUserPermissions.CREATE_OWN,
        relation = ResumePrincipalRelations.NONE
    ) to true,

    //Read
    AccessTableConditions(
        operation = ResumeContext.ResumeOperations.READ,
        permission = ResumeUserPermissions.READ_OWN,
        relation = ResumePrincipalRelations.OWN
    ) to true,
    AccessTableConditions(
        operation = ResumeContext.ResumeOperations.READ,
        permission = ResumeUserPermissions.READ_PUBLIC,
        relation = ResumePrincipalRelations.PUBLIC
    ) to true,

    //Update
    AccessTableConditions(
        operation = ResumeContext.ResumeOperations.UPDATE,
        permission = ResumeUserPermissions.UPDATE_OWN,
        relation = ResumePrincipalRelations.OWN
    ) to true,

    //Delete
    AccessTableConditions(
        operation = ResumeContext.ResumeOperations.DELETE,
        permission = ResumeUserPermissions.DELETE_OWN,
        relation = ResumePrincipalRelations.NONE
    ) to true,
)