package ru.otus.otuskotlin.resume.springapp.controller

import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.service.services.ResumeServiceImpl
import java.time.Instant

@RestController
@RequestMapping("/resume")
class ResumeController (
    private val resumeService: ResumeServiceImpl
        ){

    @PostMapping("create")
    fun createResume (@RequestBody request: CreateResumeRequest):
            CreateResumeResponse {
        val context = ResumeContext(
            startTime = Instant.now()
        )
        return try {
            runBlocking { resumeService.createResume(context, request) }
        } catch (e: Exception) {
            return resumeService.error(context, e) as CreateResumeResponse
        }
    }

    @PostMapping("read")
    fun readResume(@RequestBody request: ReadResumeRequest):
            ReadResumeResponse {
        val context = ResumeContext(
            startTime = Instant.now()
        )
        return try {
            runBlocking { resumeService.readResume(context, request) }
        } catch (e: Exception) {
            return resumeService.error(context, e) as ReadResumeResponse
        }
    }

    @PostMapping("update")
    fun updateResume(@RequestBody request: UpdateResumeRequest):
            UpdateResumeResponse {
        val context = ResumeContext(
            startTime = Instant.now()
        )
        return try {
            runBlocking { resumeService.updateResume(context, request) }
        } catch (e: Exception) {
            return resumeService.error(context, e) as UpdateResumeResponse
        }
    }

    @PostMapping("delete")
    fun deleteResume(@RequestBody request: DeleteResumeRequest):
            DeleteResumeResponse {
        val context = ResumeContext(
            startTime = Instant.now()
        )
        return try {
            runBlocking { resumeService.deleteResume(context, request) }
        } catch (e: Exception) {
            return resumeService.error(context, e) as DeleteResumeResponse
        }
    }
}