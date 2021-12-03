package ru.otus.otuskotlin.resume.repo

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import java.util.*
import kotlin.test.assertEquals

abstract class RepoResumeUpdateTest {
    abstract val repo: IRepoResume

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.update(DbResumeModelRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.update(DbResumeModelRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitResume() {
        override val initObjects: List<ResumeModel> = listOf(
            createInitTestModel("update")
        )
        val updateId = initObjects.first().id
        val updateIdNotFound = ResumeIdModel(UUID.randomUUID())

        private val updateObj = ResumeModel(
            id = updateId,
            firstName = "Ivan",
            lastName = "Ivanov",
            middleName = "Ivanovich",
            age = "10",
            birthDate = "2011-10-10",
            gender = ResumeGenderModel.MALE,
            ownerId = OwnerIdModel(UUID.randomUUID()),
            visibility = ResumeVisibilityModel.REGISTERED_ONLY,
            permissions = mutableSetOf(PermissionsModel.READ, PermissionsModel.CONTACT)
        )

        private val updateObjNotFound = ResumeModel(
            id = updateIdNotFound,
            firstName = "NA",
            lastName = "NA",
            middleName = "NA",
            age = "0",
            birthDate = "2011-10-10",
            gender = ResumeGenderModel.MALE,
            ownerId = OwnerIdModel(UUID.randomUUID()),
            visibility = ResumeVisibilityModel.REGISTERED_ONLY,
            permissions = mutableSetOf(PermissionsModel.READ, PermissionsModel.CONTACT)
        )
    }
}