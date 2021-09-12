package ru.otus.otuskotlin.resume.backend.common.models

@JvmInline
value class ResumeIdModel(val id: String) {
    companion object {
        val NONE = ResumeIdModel("")
    }

    fun asString() = id
}
