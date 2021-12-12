package ru.otus.otuskotlin.resume.repo

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import java.util.*
import kotlin.test.assertEquals

abstract class RepoResumeReadTest {
    abstract val repo: IRepoResume

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.read(DbResumeIdRequest(successId)) }

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

    companion object: BaseInitResume() {
        override val initObjects: List<ResumeModel> = listOf(
            createInitTestModel("read")
        )
        private val readResumeStub = initObjects.first()
        val successId = readResumeStub.id
        val readObjNotFoundId = ResumeIdModel(UUID.randomUUID())

    }
}