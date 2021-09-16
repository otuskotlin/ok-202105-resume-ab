package ru.otus.otuskotlin.resume.backend.common.models

@JvmInline
value class OwnerIdModel(val id: String) {
    companion object {
        val NONE = OwnerIdModel("")
    }

    fun asString() = id
}