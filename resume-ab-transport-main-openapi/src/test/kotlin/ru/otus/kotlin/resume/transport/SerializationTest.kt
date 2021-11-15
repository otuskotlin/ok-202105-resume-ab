package ru.otus.kotlin.resume.transport

import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.resume.openapi.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTest {
    private val requestId = "123abc"
    private val createRequest = CreateResumeRequest(
        requestId = requestId,
        createResume = CreatableResume(
            firstName = "Ivan",
            lastName = "Ivanov",
            middleName = "Ivanovich",
            age = "30",
            birthDate = "2000-01-01",
            gender = CreatableResume.Gender.MALE,
        )
    )

    private val om = ObjectMapper()

    @Test
    fun serializationTest() {
        val json = om.writeValueAsString(createRequest)
        println(json)
        assertTrue("json must contain discriminator") {
            json.contains(""""messageType":"${createRequest::class.simpleName}"""")
        }
        assertTrue("json must serialize messageId field") {
            json.contains(""""requestId":"$requestId"""")
        }
    }

    @Test
    fun deserializeTest() {
        val json = om.writeValueAsString(createRequest)
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateResumeRequest

        assertEquals(requestId, deserialized.requestId)
    }
}