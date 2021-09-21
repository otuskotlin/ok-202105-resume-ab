package ru.otus.otuskotln.resume.ktor

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object Utils {
    val mapper = jacksonObjectMapper()

    fun <T : List<*>> assertListEquals(expected: T, actual: T, checkOrder: Boolean, message: String? = null) {
        if (checkOrder) {
            assertEquals(expected, actual, message)
        } else {
            assertTrue(
                expected.size == actual.size && expected.containsAll(actual) && actual.containsAll(expected),
                "Expected equal unordered list <$expected>, actual <$actual>."
            )
        }
    }

    val stubDebugSuccess = BaseDebugRequest(
        mode = BaseDebugRequest.Mode.STUB,
        stubCase = BaseDebugRequest.StubCase.SUCCESS
    )

    val stubResponseResume = ResponseResume(
        firstName = Ivan.getModel().firstName,
        lastName = Ivan.getModel().lastName,
        middleName = Ivan.getModel().middleName,
        age = Ivan.getModel().age,
        birthDate = Ivan.getModel().birthDate,
        gender = ResponseResume.Gender.valueOf(Ivan.getModel().gender.name),
        ownerId = Ivan.getModel().ownerId.id,
        visibility = ResumeVisibility.valueOf(Ivan.getModel().visibility.name),
        id = Ivan.getModel().id.id,
        permissions = Ivan.getModel().permissions.map { ResumePermissions.valueOf(it.toString()) }.toSet()
    )

    val stubUpdatableResume = UpdatableResume(
        firstName = stubResponseResume.firstName,
        lastName = stubResponseResume.lastName,
        middleName = stubResponseResume.middleName,
        age = stubResponseResume.age,
        birthDate = stubResponseResume.birthDate,
        gender = UpdatableResume.Gender.valueOf(stubResponseResume.gender?.let { it.name } ?: "none"),
        ownerId = stubResponseResume.ownerId,
        visibility = stubResponseResume.visibility,
        id = stubResponseResume.id
    )

    val stubCreatableResume = CreatableResume(
        firstName = stubResponseResume.firstName,
        lastName = stubResponseResume.lastName,
        middleName = stubResponseResume.middleName,
        age = stubResponseResume.age,
        birthDate = stubResponseResume.birthDate,
        gender = CreatableResume.Gender.valueOf(stubResponseResume.gender?.let { it.name } ?: "none"),
        ownerId = stubResponseResume.ownerId,
        visibility = stubResponseResume.visibility,
    )
}