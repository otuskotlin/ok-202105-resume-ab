package ru.otus.otuskotlin.resume.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumePrincipalModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeStubCase
import ru.otus.otuskotlin.resume.backend.common.models.ResumeUserGroups
import java.time.Instant
import kotlin.test.assertEquals
/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
class ResumeCrudTest {
    private val crud = ResumeCrud()
    @Test
    fun initTest() {
        val context = ResumeContext(
            stubCase = ResumeStubCase.SUCCESS,
            operation = ResumeContext.ResumeOperations.INIT
        )
        runBlocking { crud.init(context) }
        assertEquals(CorStatus.SUCCESS, context.status)
    }

    @Test
    fun createTest() {
        val context = ResumeContext(
            startTime = Instant.now(),
            operation = ResumeContext.ResumeOperations.CREATE,
            requestResume = Ivan.getModel{ id = ResumeIdModel.NONE },
            stubCase = ResumeStubCase.SUCCESS,
            principal = ResumePrincipalModel(
                id = Ivan.getModel().ownerId,
                firstName = "Ivan",
                middleName = "Ivanovich",
                lastName = "Ivanov",
                groups = setOf(ResumeUserGroups.TEST, ResumeUserGroups.USER)
            ),
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = Ivan.getModel()
        with(context.responseResume) {
            assertEquals(expected.id, id)
            assertEquals(expected.firstName, firstName)
            assertEquals(expected.lastName, lastName)
            assertEquals(expected.middleName, middleName)
            assertEquals(expected.age, age)
            assertEquals(expected.birthDate, birthDate)
            assertEquals(expected.gender, gender)
            assertEquals(expected.ownerId, ownerId)
            assertEquals(expected.permissions, permissions)
            assertEquals(expected.visibility, visibility)
        }
    }

    @Test
    fun creadTest() {
        val context = ResumeContext(
            startTime = Instant.now(),
            operation = ResumeContext.ResumeOperations.READ,
            requestResumeId = Ivan.getModel().id,
            stubCase = ResumeStubCase.SUCCESS,
            principal = ResumePrincipalModel(
                id = Ivan.getModel().ownerId,
                firstName = "Ivan",
                middleName = "Ivanovich",
                lastName = "Ivanov",
                groups = setOf(ResumeUserGroups.TEST, ResumeUserGroups.USER)
            ),
        )
        runBlocking {
            crud.read(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = Ivan.getModel()
        with(context.responseResume) {
            assertEquals(expected.id, id)
            assertEquals(expected.firstName, firstName)
            assertEquals(expected.lastName, lastName)
            assertEquals(expected.middleName, middleName)
            assertEquals(expected.age, age)
            assertEquals(expected.birthDate, birthDate)
            assertEquals(expected.gender, gender)
            assertEquals(expected.ownerId, ownerId)
            assertEquals(expected.permissions, permissions)
            assertEquals(expected.visibility, visibility)
        }
    }

    @Test
    fun updateTest() {
        val context = ResumeContext(
            startTime = Instant.now(),
            operation = ResumeContext.ResumeOperations.UPDATE,
            requestResume = Ivan.getModel(),
            stubCase = ResumeStubCase.SUCCESS,
            principal = ResumePrincipalModel(
                id = Ivan.getModel().ownerId,
                firstName = "Ivan",
                middleName = "Ivanovich",
                lastName = "Ivanov",
                groups = setOf(ResumeUserGroups.TEST, ResumeUserGroups.USER)
            ),
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = Ivan.getModel()
        with(context.responseResume) {
            assertEquals(expected.id, id)
            assertEquals(expected.firstName, firstName)
            assertEquals(expected.lastName, lastName)
            assertEquals(expected.middleName, middleName)
            assertEquals(expected.age, age)
            assertEquals(expected.birthDate, birthDate)
            assertEquals(expected.gender, gender)
            assertEquals(expected.ownerId, ownerId)
            assertEquals(expected.permissions, permissions)
            assertEquals(expected.visibility, visibility)
        }
    }

    @Test
    fun deleteTest() {
        val context = ResumeContext(
            startTime = Instant.now(),
            operation = ResumeContext.ResumeOperations.DELETE,
            requestResumeId = Ivan.getModel().id,
            stubCase = ResumeStubCase.SUCCESS,
            principal = ResumePrincipalModel(
                id = Ivan.getModel().ownerId,
                firstName = "Ivan",
                middleName = "Ivanovich",
                lastName = "Ivanov",
                groups = setOf(ResumeUserGroups.TEST, ResumeUserGroups.USER)
            ),
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = Ivan.getModel()
        with(context.responseResume) {
            assertEquals(expected.id, id)
            assertEquals(expected.firstName, firstName)
            assertEquals(expected.lastName, lastName)
            assertEquals(expected.middleName, middleName)
            assertEquals(expected.age, age)
            assertEquals(expected.birthDate, birthDate)
            assertEquals(expected.gender, gender)
            assertEquals(expected.ownerId, ownerId)
            assertEquals(expected.permissions, permissions)
            assertEquals(expected.visibility, visibility)
        }
    }
}