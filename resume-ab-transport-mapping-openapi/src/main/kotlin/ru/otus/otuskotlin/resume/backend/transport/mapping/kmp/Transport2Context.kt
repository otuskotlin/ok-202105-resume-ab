package ru.otus.otuskotlin.resume.backend.transport.mapping.kmp

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.openapi.models.*

private fun CreatableResume.toModel() = ResumeModel(
    lastName = lastName?:"",
    firstName = firstName?:"",
    middleName = middleName?:"",
    age = age?:"",
    ownerId = OwnerIdModel(ownerId?:""),
    birthDate = birthDate?:"",
    gender = gender?.let { ResumeGenderModel.valueOf(it.name) }?:ResumeGenderModel.NONE,
    visibility = visibility?.let { ResumeVisibilityModel.valueOf(it.name) }?:ResumeVisibilityModel.NONE
)

private fun UpdatableResume.toModel() = ResumeModel(
    id = ResumeIdModel(id?:""),
    lastName = lastName?:"",
    firstName = firstName?:"",
    middleName = middleName?:"",
    age = age?:"",
    ownerId = OwnerIdModel(ownerId?:""),
    birthDate = birthDate?:"",
    gender = gender?.let { ResumeGenderModel.valueOf(it.name) }?:ResumeGenderModel.NONE,
    visibility = visibility?.let { ResumeVisibilityModel.valueOf(it.name) }?:ResumeVisibilityModel.NONE
)

fun ResumeContext.setQuery(query: InitResumeRequest) = apply {
    onRequest = query.requestId?:""
}

fun ResumeContext.setQuery(query: CreateResumeRequest) = apply {
    onRequest = query.requestId?:""
    requestResume = query.createResume?.toModel()?: ResumeModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun ResumeContext.setQuery(query: ReadResumeRequest) = apply {
    onRequest = query.requestId?:""
    requestResumeId = ResumeIdModel(query.readResumeId?:"")
    stubCase = query.debug?.stubCase.toModel()
}

fun ResumeContext.setQuery(query: UpdateResumeRequest) = apply {
    onRequest = query.requestId?:""
    requestResume = query.createResume?.toModel()?: ResumeModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun ResumeContext.setQuery(query: DeleteResumeRequest) = apply {
    onRequest = query.requestId?:""
    requestResumeId = ResumeIdModel(query.deleteResumeId?:"")
    stubCase = query.debug?.stubCase.toModel()
}

private fun BaseDebugRequest.StubCase?.toModel() = when(this) {
    BaseDebugRequest.StubCase.SUCCESS -> ResumeStubCase.SUCCESS
    BaseDebugRequest.StubCase.DATABASE_ERROR -> ResumeStubCase.DATABASE_ERROR
    null -> ResumeStubCase.NONE
}