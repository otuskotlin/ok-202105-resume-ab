package ru.otus.otuskotlin.resume.repo

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import java.util.*
import kotlin.test.assertEquals

abstract class RepoResumeDeleteTest {
    abstract val repo: IRepoResume

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.delete(DbResumeIdRequest(deleteObj)) }

        assertEquals(true, result.isSuccess)
        assertEquals(deleteSuccessStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() {
        val result = runBlocking { repo.delete(DbResumeIdRequest(deleteObjNotFound)) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitResume() {
        override val initObjects: List<ResumeModel> = listOf(
            createInitTestModel("delete")
        )

        private val deleteSuccessStub = initObjects.first()
        val deleteObj = deleteSuccessStub.id
        val deleteObjNotFound = ResumeIdModel(UUID.randomUUID())
    }
}