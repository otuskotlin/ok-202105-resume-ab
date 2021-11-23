package resume.stubs

import ru.otus.otuskotlin.resume.backend.common.models.*

object Ivan {
    private val stubReady = ResumeModel(
        id = ResumeIdModel(id = "123"),
        firstName = "Ivan",
        lastName = "Ivanov",
        middleName = "Ivanovich",
        age = "10",
        birthDate = "2011-10-10",
        gender = ResumeGenderModel.MALE,
        ownerId = OwnerIdModel(id = "111111-22222"),
        visibility = ResumeVisibilityModel.PUBLIC,
        permissions = mutableSetOf(PermissionsModel.READ)
    )

    private val stubInProgress = ResumeModel(
        id = ResumeIdModel(id = "1234567890"),
        firstName = "Ivan",
        lastName = "Ivanov",
        middleName = "Ivanovich",
        age = "20",
        birthDate = "2001-10-10",
        gender = ResumeGenderModel.MALE,
        ownerId = OwnerIdModel(id = "22-33"),
        visibility = ResumeVisibilityModel.OWNER_ONLY,
        permissions = mutableSetOf(PermissionsModel.NONE)
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