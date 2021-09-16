package ru.otus.otuskotlin.resume.aws.services

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext

interface ResumeService {
    fun createResume(context: ResumeContext): ResumeContext
    fun readResume(context: ResumeContext):ResumeContext
    fun deleteResume(context: ResumeContext): ResumeContext
    fun updateResume(context: ResumeContext): ResumeContext
}
