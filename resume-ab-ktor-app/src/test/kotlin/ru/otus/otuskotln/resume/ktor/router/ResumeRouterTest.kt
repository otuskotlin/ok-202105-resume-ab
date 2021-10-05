package ru.otus.otuskotln.resume.ktor.router

import org.junit.Test
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotln.resume.ktor.Utils
import ru.otus.otuskotln.resume.ktor.Utils.stubCreatableResume
import ru.otus.otuskotln.resume.ktor.Utils.stubUpdatableResume
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ResumeRouterTest: RouterTest() {
    @Test
    fun createRequest() {
        val data = CreateResumeRequest(createResume = stubCreatableResume, debug = Utils.stubDebugSuccess)

        testPostRequest<CreateResumeResponse>(data, "/resume/create") {
            assertEquals(CreateResumeResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseResume.copy(permissions = null), createdResume)
        }
    }

    @Test
    fun testPostAdRead() {
        val data = ReadResumeRequest(readResumeId = "1", debug = Utils.stubDebugSuccess)

        testPostRequest<ReadResumeResponse>(data.copy(readResumeId = "123"), "/resume/read") {
            assertEquals(ReadResumeResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseResume, readResume)
        }
    }

    @Test
    fun testPostAdUpdate() {
        val data = UpdateResumeRequest(createResume = stubUpdatableResume, debug = Utils.stubDebugSuccess)

        testPostRequest<UpdateResumeResponse>(data, "/resume/update") {
            assertEquals(UpdateResumeResponse.Result.SUCCESS, result)
            assertNull(errors)
            // permissions must be null according to stubs
            assertEquals(Utils.stubResponseResume.copy(permissions = null), updatedResume)
        }
    }

    @Test
    fun testPostAdDelete() {
        val data = DeleteResumeRequest(deleteResumeId = "NONE", debug = Utils.stubDebugSuccess)

        testPostRequest<DeleteResumeResponse>(data.copy(deleteResumeId = "123"), "/resume/delete") {
            assertEquals(DeleteResumeResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }
}