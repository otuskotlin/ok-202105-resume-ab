package ru.otus.otuskotlin.resume.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import ru.otus.otuskotlin.resume.aws.services.ResumeServiceImpl
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.*
import ru.otus.otuskotlin.resume.openapi.models.CreateResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.DeleteResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.ReadResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.UpdateResumeRequest

class CreateResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse =
        ResumeContext()
            .setQuery(input.toTransportModel<CreateResumeRequest>())
            .let { ResumeServiceImpl().createResume(it) }
            .toCreateResponse()
            .toAPIGatewayV2HTTPResponse()
}

class ReadResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context?): APIGatewayV2HTTPResponse =
        ResumeContext()
            .setQuery(input.toTransportModel<ReadResumeRequest>())
            .let { ResumeServiceImpl().readResume(it) }
            .toReadResponse()
            .toAPIGatewayV2HTTPResponse()
}

class UpdateResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context?): APIGatewayV2HTTPResponse =
        ResumeContext()
            .setQuery(input.toTransportModel<UpdateResumeRequest>())
            .let { ResumeServiceImpl().updateResume(it) }
            .toUpdateResponse()
            .toAPIGatewayV2HTTPResponse()
}

class DeleteResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context?): APIGatewayV2HTTPResponse =
        ResumeContext()
            .setQuery(input.toTransportModel<DeleteResumeRequest>())
            .let { ResumeServiceImpl().deleteResume(it) }
            .toDeleteResponse()
            .toAPIGatewayV2HTTPResponse()
}