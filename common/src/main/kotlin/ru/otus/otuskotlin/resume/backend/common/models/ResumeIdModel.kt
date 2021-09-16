package ru.otus.otuskotlin.resume.backend.common.models

@JvmInline
value class ResumeIdModel(private val value: String) {
    companion object {
        val NONE = ResumeIdModel("")
    }

    fun asString() = value
}
