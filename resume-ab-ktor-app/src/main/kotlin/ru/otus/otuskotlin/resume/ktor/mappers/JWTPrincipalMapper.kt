package ru.otus.otuskotlin.resume.ktor.mappers

import io.ktor.auth.jwt.*
import ru.otus.otuskotlin.resume.backend.common.models.OwnerIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumePrincipalModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeUserGroups

fun JWTPrincipal?.toModel() = this?.run {
    ResumePrincipalModel(
        id = payload.getClaim("id").asString()?.let { OwnerIdModel(it) } ?: OwnerIdModel.NONE,
        firstName = payload.getClaim("firstName").asString() ?: "",
        middleName = payload.getClaim("middleName").asString() ?: "",
        lastName = payload.getClaim("lastName").asString() ?: "",
        groups = payload
            .getClaim("groups")
            ?.asList(String::class.java)
            ?.mapNotNull {
                try {
                    ResumeUserGroups.valueOf(it)
                } catch (e: Throwable) {
                    null
                }
            }?.toSet() ?: emptySet()
    )
} ?: ResumePrincipalModel.NONE