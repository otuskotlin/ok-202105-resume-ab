package ru.otus.otuskotlin.resume.repo

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class RepoResumeCreateTest {
    abstract val repo: IRepoResume

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(DbResumeModelRequest(createObj)) }
        val expected = createObj.copy(id = result.result?.id ?: ResumeIdModel.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.result)
        assertNotEquals(ResumeIdModel.NONE, result.result?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitResume("create") {
        private val createObj = ResumeModel(
        firstName = "Ivan",
        lastName = "Ivanov",
        middleName = "Ivanovich",
        age = "10",
        birthDate = "2011-10-10",
        gender = ResumeGenderModel.MALE,
        ownerId = OwnerIdModel("owner-123"),
        visibility = ResumeVisibilityModel.REGISTERED_ONLY,
        permissions = mutableSetOf(PermissionsModel.READ, PermissionsModel.UPDATE)
        )
        override val initObjects: List<ResumeModel> = emptyList()
    }
}