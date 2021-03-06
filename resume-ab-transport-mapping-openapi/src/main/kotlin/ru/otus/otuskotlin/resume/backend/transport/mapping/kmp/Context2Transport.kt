package ru.otus.otuskotlin.resume.backend.transport.mapping.kmp

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.exceptions.ResumeOperationNotSet
import ru.otus.otuskotlin.resume.backend.common.models.*
import ru.otus.otuskotlin.resume.openapi.models.*
import java.time.Instant
import java.util.*

fun ResumeContext.toInitResponse() = InitResumeResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if(errors.find { it.level == IError.Level.ERROR } == null) InitResumeResponse.Result.SUCCESS
    else InitResumeResponse.Result.ERROR
)

private fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() }
)

fun ResumeContext.toReadResponse() = ReadResumeResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    readResume = responseResume.takeIf { it != ResumeModel() }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadResumeResponse.Result.SUCCESS
    else ReadResumeResponse.Result.ERROR
)

private fun ResumeModel.toTransport() = ResponseResume(
    id = id.takeIf { it != ResumeIdModel.NONE }?.asString(),
    lastName = lastName.takeIf { it.isNotBlank() },
    firstName = firstName.takeIf { it.isNotBlank() },
    middleName = middleName.takeIf { it.isNotBlank() },
    age = age.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != OwnerIdModel.NONE }?.asString(),
    birthDate = birthDate.takeIf { it.isNotBlank() },
    gender = gender.takeIf { it != ResumeGenderModel.NONE }?.let { ResponseResume.Gender.valueOf(it.name) },
    visibility = visibility.takeIf { it != ResumeVisibilityModel.NONE }?.let { ResumeVisibility.valueOf(it.name) },
    permissions = permissions.takeIf { it.isNotEmpty() }?.filter { it != PermissionModel.NONE }
        ?.map { ResumePermissions.valueOf(it.name) }?.toSet(),
)
fun ResumeContext.toCreateResponse() = CreateResumeResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createdResume = responseResume.takeIf { it != ResumeModel() }?.toTransport(),
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateResumeResponse.Result.SUCCESS
    else CreateResumeResponse.Result.ERROR
)

fun ResumeContext.toUpdateResponse() = UpdateResumeResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    updatedResume = responseResume.takeIf { it != ResumeModel() }?.toTransport(),
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateResumeResponse.Result.SUCCESS
    else UpdateResumeResponse.Result.ERROR
)

fun ResumeContext.toDeleteResponse() = DeleteResumeResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    deletedResume = responseResume.takeIf { it != ResumeModel() }?.toTransport(),
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteResumeResponse.Result.SUCCESS
    else DeleteResumeResponse.Result.ERROR
)

fun ResumeContext.toResponse() = when(operation) {
    ResumeContext.ResumeOperations.INIT -> toInitResponse()
    ResumeContext.ResumeOperations.CREATE -> toCreateResponse()
    ResumeContext.ResumeOperations.READ -> toReadResponse()
    ResumeContext.ResumeOperations.UPDATE -> toUpdateResponse()
    ResumeContext.ResumeOperations.DELETE -> toDeleteResponse()
    ResumeContext.ResumeOperations.NONE -> throw ResumeOperationNotSet("Operation for error response is not set")
}

fun ResumeContext.toLog(logId: String) = CommonLogModel(
    messageId = UUID.randomUUID().toString(),
    messageTime = Instant.now().toString(),
    source = "ok-resume",
    logId = logId,
    resume = ResumeLogModel(
        requestAdId = requestResumeId.takeIf { it != ResumeIdModel.NONE }?.asString(),
        requestAd = requestResume.takeIf { it != ResumeModel() }?.toTransport(),
        responseAd = responseResume.takeIf { it != ResumeModel() }?.toTransport(),
    ),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
)