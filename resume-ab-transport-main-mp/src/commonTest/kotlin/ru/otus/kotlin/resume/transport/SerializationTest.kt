package ru.otus.kotlin.resume.transport

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.otus.kotlin.resume.mp.transport.jsonSerializer
import ru.otus.otuskotlin.resume.mp.models.BaseMessage
import ru.otus.otuskotlin.resume.mp.models.CreateResumeRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SerializationTest {

    @Test
    fun resumeSerializeTest() {
        val json = Json {
            prettyPrint = true
        }
        val dto = CreateResumeRequest(
            requestId = "1234asdf"
        )

        val serializedString = json.encodeToString(CreateResumeRequest.serializer(), dto)
        assertContains(serializedString, Regex("requestId\":\\s*\"1234asdf"))
    }

    @Test
    fun resumeSerializePolymorphicTest() {
        val jsonRequest = jsonSerializer
        val dto: BaseMessage = CreateResumeRequest(
            requestId = "12345asdf"
        )
        val serializedString = jsonRequest.encodeToString(dto)
        assertContains(serializedString, Regex("requestId\":\\s*\"12345asdf"))
        val deserializedDto = jsonRequest.decodeFromString<BaseMessage>(serializedString)
        assertEquals("12345asdf", (deserializedDto as CreateResumeRequest).requestId)

    }
}