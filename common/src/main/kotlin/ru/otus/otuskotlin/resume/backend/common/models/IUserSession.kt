package ru.otus.otuskotlin.resume.backend.common.models

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext

interface IUserSession<T> {
    val fwSession: T

    suspend fun notifyResumeChanged(context: ResumeContext)
}

object EmptySession : IUserSession<Unit> {
    override val fwSession: Unit = Unit

    override suspend fun notifyResumeChanged(context: ResumeContext) {
        TODO("Not yet implemented")
    }
}