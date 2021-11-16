package ru.otus.otuskotlin.resume.repo

import ru.otus.otuskotlin.resume.backend.common.models.*

abstract class BaseInitResume(val op: String): IInitObject<ResumeModel> {
    fun createInitTestModel(
        suf: String,
        ownerId: OwnerIdModel = OwnerIdModel("owner-123"),
        permissions: Set<PermissionsModel> = setOf(PermissionsModel.READ)
    ) = ResumeModel(
        id = ResumeIdModel(id = "resume-repo-$op-$suf"),
        firstName = "$suf Ivan",
        lastName = "$suf Ivanov",
        middleName = "$suf Ivanovich",
        age = "10",
        birthDate = "2011-10-10",
        gender = ResumeGenderModel.MALE,
        ownerId = ownerId,
        visibility = ResumeVisibilityModel.OWNER_ONLY,
        permissions = permissions.toMutableSet()
    )
}