package ru.otus.otuskotlin.resume.logging

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import java.time.Instant

suspend fun <T> Logger.wrapWithLogging(
    id: String = "",
    block: suspend () -> T,
): T = try {
    val timeStart = Instant.now()
    info("Entering $id")
    block().also {
        val diffTime = Instant.now().toEpochMilli() - timeStart.toEpochMilli()
        info("Finishing $id", Pair("metricHandleTime", diffTime))
    }
} catch (e: Throwable) {
    error("Failing $id", e)
    throw e
}

fun resumeLogger(loggerId: String): ResumeLogWrapper = resumeLogger(
    logger = LoggerFactory.getLogger(loggerId) as Logger
)

fun resumeLogger(cls: Class<out Any>): ResumeLogWrapper = resumeLogger(
    logger = LoggerFactory.getLogger(cls) as Logger
)

fun resumeLogger(logger: Logger): ResumeLogWrapper = ResumeLogWrapper(
    logger = logger,
    loggerId = logger.name,
)