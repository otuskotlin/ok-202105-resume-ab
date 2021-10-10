package ru.otus.otuskotlin.resume.service.services.exceptions

import ru.otus.otuskotlin.resume.openapi.models.BaseMessage

class DataNotAllowedException(msg: String, request: BaseMessage) : Throwable("$msg: $request")
