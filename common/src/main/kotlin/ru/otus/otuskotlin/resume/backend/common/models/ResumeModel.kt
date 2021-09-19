package ru.otus.otuskotlin.resume.backend.common.models

data class ResumeModel(
    var id: ResumeIdModel = ResumeIdModel.NONE,
    var lastName: String = "",
    var firstName: String = "",
    var middleName: String = "",
    var age: String = "",
    var birthDate: String = "",
    var gender: ResumeGenderModel = ResumeGenderModel.NONE,
    var ownerId: OwnerIdModel = OwnerIdModel.NONE,
    var visibility: ResumeVisibilityModel = ResumeVisibilityModel.NONE,
    var permissions: MutableSet<PermissionsModel> = mutableSetOf()
)
