package ru.otus.otuskotlin.resume.springapp.service

import org.springframework.stereotype.Service
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.context.addError
import ru.otus.otuskotlin.resume.backend.common.models.IError

@Service
class ResumeServiceImpl : ResumeService {
    override fun createResume(context: ResumeContext): ResumeContext {
        val requestCreateResume = context.requestResume
        return context.apply {
            responseResume = requestCreateResume
        }
    }

    override fun readResume(context: ResumeContext): ResumeContext {
        val requestedId = context.requestResumeId.id
        val shouldReturnStub = Ivan.isCorrectId(requestedId)

        return if (shouldReturnStub) {
            context.apply { responseResume = Ivan.getModel() }
        } else {
            context.addError {
                field = "requestedResumeId"
                message = "Not found resume by id $requestedId"
            }
        }
    }

    override fun updateResume(context: ResumeContext) = context.apply {
        responseResume = requestResume
    }

    override fun deleteResume(context: ResumeContext): ResumeContext {
        val shouldReturnStub = Ivan.isCorrectId(context.requestResumeId.id)

        return if(shouldReturnStub) {
            context.apply { responseResume = requestResume }
        } else {
            context.addError {
                field = "id"
                level = IError.Level.WARNING
                message = "Resume with id ${context.requestResume.id.id} doesn't exist"
            }
        }
    }
}