package ru.otus.otuskotlin.resume.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.IError
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeStubCase
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
            requestResume = Ivan.getModel{ id = ResumeIdModel.NONE },
            stubCase = ResumeStubCase.SUCCESS,
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
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        assertEquals(0, context.errors.size)
    }

}