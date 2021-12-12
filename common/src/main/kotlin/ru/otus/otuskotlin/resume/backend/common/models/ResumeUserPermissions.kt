package ru.otus.otuskotlin.resume.backend.common.models

enum class ResumeUserPermissions {
    CREATE_OWN,
    READ_OWN,
    READ_GROUP,
    READ_PUBLIC,
    UPDATE_OWN,
    DELETE_OWN,
}