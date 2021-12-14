package ru.otus.otuskotlin.resume.ktor.controller

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.ktor.mappers.toModel
import ru.otus.otuskotlin.resume.logging.ResumeLogWrapper
import java.time.Instant

suspend inline fun <reified Request : Any, reified Response : Any> ApplicationCall.handleRequest(
    logId: String,
    logger: ResumeLogWrapper,
    crossinline block: suspend ResumeContext.(Request) -> Response,
    crossinline except: suspend ResumeContext.(Throwable) -> Response
) {
    logger.doWithLogging(logId) {
        val context = ResumeContext(startTime = Instant.now(), principal = principal<JWTPrincipal>().toModel())

        val res = try {
            val req = receive<Request>()
            context.block(req)
        } catch (e: Throwable) {
            withContext(NonCancellable) {
                context.except(e)
            }
        }
        respond(res)
    }
}
