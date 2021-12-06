package ru.otus.otuskotlin.resume.backend.common.models

data class ResumePrincipalModel(
    val id: OwnerIdModel = OwnerIdModel.NONE,
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val groups: Set<ResumeUserGroups> = emptySet()
) {
    companion object {
        val NONE = ResumePrincipalModel()
    }
}
