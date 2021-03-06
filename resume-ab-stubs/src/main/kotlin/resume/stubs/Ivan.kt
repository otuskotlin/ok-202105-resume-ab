package resume.stubs

import ru.otus.otuskotlin.resume.backend.common.models.*

object Ivan {
    private val stubReady = ResumeModel(
        id = ResumeIdModel(id = "f62d0c1c-bccd-486f-9364-857581cd6282"),
        firstName = "Ivan",
        lastName = "Ivanov",
        middleName = "Ivanovich",
        age = "10",
        birthDate = "2011-10-10",
        gender = ResumeGenderModel.MALE,
        ownerId = OwnerIdModel(id = "111111-22222"),
        visibility = ResumeVisibilityModel.PUBLIC,
        permissions = mutableSetOf(PermissionModel.READ)
    )

    private val stubInProgress = ResumeModel(
        id = ResumeIdModel(id = "f62d0c1c-bccd-486f-9364-857581cd6282"),
        firstName = "Ivan",
        lastName = "Ivanov",
        middleName = "Ivanovich",
        age = "20",
        birthDate = "2001-10-10",
        gender = ResumeGenderModel.MALE,
        ownerId = OwnerIdModel(id = "22-33"),
        visibility = ResumeVisibilityModel.OWNER_ONLY,
        permissions = mutableSetOf(PermissionModel.NONE)
    )

    fun getModel(model: (ResumeModel.() -> Unit)? = null) = stubReady.copy().also { stub ->
        model?.let { stub.apply(it)}
    }

    fun isCorrectId(id: String) = id == "123"

    fun getModels() = listOf(
        stubReady,
        stubInProgress
    )

    fun ResumeModel.update(updateableResume: ResumeModel) = apply {
        firstName = updateableResume.firstName
        lastName = updateableResume.lastName
        middleName = updateableResume.middleName
        age = updateableResume.age
        birthDate =updateableResume.birthDate
        gender = updateableResume.gender
        ownerId = updateableResume.ownerId
        visibility = updateableResume.visibility
        permissions.addAll(updateableResume.permissions)
    }
}