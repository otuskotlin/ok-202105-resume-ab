package ru.otus.otuskotln.resume.ktor.auth

import io.ktor.http.*
import org.junit.Test
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.configs.KtorAuthConfig
import ru.otus.otuskotlin.resume.openapi.models.ReadResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.ReadResumeResponse
import ru.otus.otuskotln.resume.ktor.Utils
import ru.otus.otuskotln.resume.ktor.router.RouterTest
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class AuthWrongIssuer : RouterTest() {
    @Test
    fun authPositiveTest() {
        val data = ReadResumeRequest(readResumeId = "99999", debug = Utils.stubDebugSuccess)

        testPostRequest<ReadResumeResponse>(
            body=data,
            uri="/resume/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy()
            )
        ) {
            assertEquals(ReadResumeResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(Utils.stubResponseResume.copy(id = "99999"), readResume)
        }
    }
    @Test
    fun authWrongIssuerTest() {
        val data = ReadResumeRequest(readResumeId = "99999", debug = Utils.stubDebugSuccess)

        testPostRequest<ReadResumeResponse>(
            body=data,
            uri="/resume/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    issuer = "some other company"
                )
            ),
            result = HttpStatusCode.Unauthorized,
        )
    }
    @Test
    fun authWrongSecretTest() {
        val data = ReadResumeRequest(readResumeId = "99999", debug = Utils.stubDebugSuccess)

        testPostRequest<ReadResumeResponse>(
            body=data,
            uri="/resume/read",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    secret = "wrong secret"
                )
            ),
            result = HttpStatusCode.Unauthorized,
        )
    }
}