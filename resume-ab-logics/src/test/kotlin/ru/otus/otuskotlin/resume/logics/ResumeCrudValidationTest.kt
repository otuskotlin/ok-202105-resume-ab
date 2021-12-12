package ru.otus.otuskotlin.resume.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.*
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
class ResumeCrudValidationTest {
    private val crud = ResumeCrud()

    @Test
    fun createTest() {
        val context = ResumeContext(
            startTime = Instant.now(),
            operation = ResumeContext.ResumeOperations.CREATE,
            requestResume = Ivan.getModel{ id = ResumeIdModel("f62d0c1c-bccd-486f-9364-857581cd6282") },
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
        assertEquals(CorStatus.ERROR, context.status)
        assertNotNull(context.errors.find { it.level == IError.Level.ERROR })
    }

    @Test
    fun createSuccTest() {
        val context = ResumeContext(
            startTime = Instant.now(),
            operation = ResumeContext.ResumeOperations.CREATE,
            requestResume = Ivan.getModel{ id = Ivan.getModel().id },
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
        assertEquals(0, context.errors.size)
    }

}