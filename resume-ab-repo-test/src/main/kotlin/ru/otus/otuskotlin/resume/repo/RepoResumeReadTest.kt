package ru.otus.otuskotlin.resume.repo

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import kotlin.test.assertEquals

abstract class RepoResumeReadTest {
    abstract val repo: IRepoResume

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.read(DbResumeIdRequest(readObjId)) }

        assertEquals(true, result.isSuccess)
        assertEquals(readResumeStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() {
        val result = runBlocking { repo.read(DbResumeIdRequest(readObjNotFoundId)) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitResume("read") {
        override val initObjects: List<ResumeModel> = listOf(
            createInitTestModel("read")
        )
        val readObjId = ResumeIdModel("resume-repo-read-success")
        val readObjNotFoundId = ResumeIdModel("resume-repo-read-notFound")

        private val readResumeStub = ResumeModel(
            id = readObjId,
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
    }
}