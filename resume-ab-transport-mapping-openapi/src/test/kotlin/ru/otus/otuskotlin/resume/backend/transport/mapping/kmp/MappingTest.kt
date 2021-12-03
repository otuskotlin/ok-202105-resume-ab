package ru.otus.otuskotlin.resume.backend.transport.mapping.kmp

import org.junit.Test
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.openapi.models.ResumeVisibility
import ru.otus.otuskotlin.resume.openapi.models.UpdatableResume
import ru.otus.otuskotlin.resume.openapi.models.UpdateResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.UpdateResumeResponse
import java.util.*
import kotlin.test.assertEquals

class MappingTest {

    @Test
    fun setUpdateQueryMappingTest() {
        val query = UpdateResumeRequest(
            requestId = "123-123",
            createResume = UpdatableResume(
                id = "id-001",
                firstName = "Ivan",
                lastName = "Ivanov",
                middleName = "Ivanovich",
                age = "21",
                ownerId = "owner_id-001",
                birthDate = "2000-12-31",
                gender = UpdatableResume.Gender.MALE,
                visibility = ResumeVisibility.REGISTERED_ONLY
            )
        )

        val context = ResumeContext().setQuery(query)
        assertEquals("123-123", context.onRequest)
        assertEquals("id-001", context.requestResume.id.asString())
        assertEquals("Ivan", context.requestResume.firstName)
        assertEquals("Ivanov", context.requestResume.lastName)
        assertEquals("Ivanovich", context.requestResume.middleName)
        assertEquals("21", context.requestResume.age)
        assertEquals("owner_id-001", context.requestResume.ownerId.asString())
        assertEquals("2000-12-31", context.requestResume.birthDate)
        assertEquals(ResumeGenderModel.MALE, context.requestResume.gender)
        assertEquals(ResumeVisibilityModel.REGISTERED_ONLY, context.requestResume.visibility)
    }

    @Test
    fun updateResponseMappingTest() {
        val context = ResumeContext(
            onRequest = "1",
            responseResume = ResumeModel(
                id = ResumeIdModel("f62d0c1c-bccd-486f-9364-857581cd6282"),
                firstName = "Petr",
                lastName = "Petrov",
                middleName = "Petrovich",
                age = "40",
                ownerId = OwnerIdModel(UUID.randomUUID()),
                birthDate = "1981-12-31",
                gender = ResumeGenderModel.MALE,
                visibility = ResumeVisibilityModel.REGISTERED_ONLY
            ),
            errors = mutableListOf(CommonErrorModel(level = IError.Level.WARNING))
        )
        val response = context.toUpdateResponse()

        assertEquals("1", response.requestId)
        assertEquals("id-002", response.updatedResume?.id)
        assertEquals("Petr", response.updatedResume?.firstName)
        assertEquals("Petrov", response.updatedResume?.lastName)
        assertEquals("Petrovich", response.updatedResume?.middleName)
        assertEquals("40", response.updatedResume?.age)
        assertEquals("owner_id-002", response.updatedResume?.ownerId)
        assertEquals("1981-12-31", response.updatedResume?.birthDate)
        assertEquals(UpdatableResume.Gender.MALE.value, response.updatedResume?.gender?.value)
        assertEquals(ResumeVisibility.REGISTERED_ONLY, response.updatedResume?.visibility)
        assertEquals(UpdateResumeResponse.Result.SUCCESS, response.result)
        assertEquals(1, response.errors?.size)
    }
}