package ru.otus.otuskotlin.resume.repo

import ru.otus.otuskotlin.resume.backend.common.models.*
import java.util.*

abstract class BaseInitResume: IInitObject<ResumeModel> {
    fun createInitTestModel(
        suf: String,
        ownerId: OwnerIdModel = OwnerIdModel(UUID.randomUUID()),
        permissions: Set<PermissionsModel> = setOf(PermissionsModel.READ)
    ) = ResumeModel(
        id = ResumeIdModel(UUID.randomUUID()),
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