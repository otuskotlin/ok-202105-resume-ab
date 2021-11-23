package ru.otus.otuskotlin.resume.reo.inmemory.models

import ru.otus.otuskotlin.resume.backend.common.models.*

data class ResumeRow(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val age: String? = null,
    val birthDate: String? = null,
    val gender: String?  = null,
    val ownerId: String? = null,
    val visibility: String? = null,
) {
    constructor(internal: ResumeModel): this(
        id = internal.id.asString().takeIf { it.isNotBlank() },
        firstName = internal.firstName.takeIf { it.isNotBlank() },
        lastName = internal.lastName.takeIf { it.isNotBlank() },
        middleName = internal.middleName.takeIf { it.isNotBlank() },
        age = internal.age.takeIf { it.isNotBlank() },
        birthDate = internal.age.takeIf { it.isNotBlank() },
        gender = internal.gender.takeIf { it != ResumeGenderModel.NONE }?.name,
        ownerId = internal.ownerId.takeIf { it != OwnerIdModel.NONE }?.asString(),
        visibility = internal.visibility.takeIf { it != ResumeVisibilityModel.NONE }?.name,
    )

    fun toInternal(): ResumeModel = ResumeModel(
        id = id?.let { ResumeIdModel(it) } ?: ResumeIdModel.NONE,
        lastName = lastName ?: "",
        firstName = firstName ?: "",
        middleName = middleName ?: "",
        age = age ?: "",
        birthDate = birthDate ?: "",
        gender = gender?.let { ResumeGenderModel.valueOf(it) } ?: ResumeGenderModel.NONE,
        ownerId = ownerId?.let {OwnerIdModel(it)} ?: OwnerIdModel.NONE,
        visibility = visibility?.let { ResumeVisibilityModel.valueOf(it) } ?: ResumeVisibilityModel.NONE
    )
}