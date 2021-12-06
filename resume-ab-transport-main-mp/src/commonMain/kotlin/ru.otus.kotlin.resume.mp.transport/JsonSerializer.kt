package ru.otus.kotlin.resume.mp.transport

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.otuskotlin.resume.mp.models.*

val jsonSerializer = Json {
    prettyPrint = true
    useAlternativeNames = true
    encodeDefaults = true
//    classDiscriminator = "messageType"
    serializersModule = SerializersModule {
        polymorphic(BaseMessage::class) {
            subclass(InitResumeRequest::class, InitResumeRequest.serializer())
            subclass(CreateResumeRequest::class, CreateResumeRequest.serializer())
            subclass(ReadResumeRequest::class, ReadResumeRequest.serializer())
            subclass(UpdateResumeRequest::class, UpdateResumeRequest.serializer())
            subclass(DeleteResumeRequest::class, DeleteResumeRequest.serializer())
            subclass(InitResumeResponse::class, InitResumeResponse.serializer())
            subclass(CreateResumeResponse::class, CreateResumeResponse.serializer())
            subclass(ReadResumeResponse::class, ReadResumeResponse.serializer())
            subclass(UpdateResumeResponse::class, UpdateResumeResponse.serializer())
            subclass(DeleteResumeResponse::class, DeleteResumeResponse.serializer())
        }
    }
}