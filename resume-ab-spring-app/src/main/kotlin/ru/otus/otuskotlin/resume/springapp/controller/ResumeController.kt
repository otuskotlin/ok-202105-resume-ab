package ru.otus.otuskotlin.resume.springapp.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.springapp.service.ResumeService

@RestController
@RequestMapping("/resume")
class ResumeController (
    private val resumeService: ResumeService
        ){

    @PostMapping("create")
    fun createResume (@RequestBody createResumeRequest: CreateResumeRequest):
            CreateResumeResponse {
        val context = ResumeContext().setQuery(createResumeRequest)
        return resumeService.createResume(context).toCreateResponse()
    }

    @PostMapping("read")
    fun readResume(@RequestBody readResumeRequest: ReadResumeRequest) =
        ResumeContext().setQuery(readResumeRequest).let {
            resumeService.readResume(it)
        }.toReadResponse()

    @PostMapping("update")
    fun updateResume(@RequestBody updateResumeRequest: UpdateResumeRequest): UpdateResumeResponse {
        return ResumeContext().setQuery(updateResumeRequest).let {
            resumeService.updateResume(it)
        }.toUpdateResponse()
    }

    @PostMapping("delete")
    fun deleteResume(@RequestBody deleteResumeRequest: DeleteResumeRequest): DeleteResumeResponse {
        return ResumeContext().setQuery(deleteResumeRequest).let {
            resumeService.deleteResume(it)
        }.toDeleteResponse()
    }
}