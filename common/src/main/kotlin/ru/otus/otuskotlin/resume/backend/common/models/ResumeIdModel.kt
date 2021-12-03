package ru.otus.otuskotlin.resume.backend.common.models

import java.util.*

@JvmInline
value class ResumeIdModel(private val id: String) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = ResumeIdModel("")
    }

    fun asUUID(): UUID = UUID.fromString(id)

    fun asString() = id
}
