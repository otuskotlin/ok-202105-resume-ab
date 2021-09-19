package ru.otus.otuskotlin.resume.aws

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.resume.aws.configs.resumeService
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.openapi.models.*
import java.time.Instant

class InitResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val request = input.toTransportModel<InitResumeRequest>()
        val context = ResumeContext(
            startTime = Instant.now()
        )
        val result = try {
            runBlocking {
                resumeService.initResume(context, request)
            }
        } catch (e: Throwable) {
            runBlocking { resumeService.error(context, e) as InitResumeResponse }
        }
        return result.toAPIGatewayV2HTTPResponse()

    }
}

class CreateResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val request = input.toTransportModel<CreateResumeRequest>()
        val context = ResumeContext(
            startTime = Instant.now()
        )
        val result = try {
            runBlocking {
                resumeService.createResume(context, request)
            }
        } catch (e: Throwable) {
            runBlocking { resumeService.error(context, e) as CreateResumeResponse }
        }
        return result.toAPIGatewayV2HTTPResponse()

    }
}

class ReadResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val request = input.toTransportModel<ReadResumeRequest>()
        val context = ResumeContext(
            startTime = Instant.now()
        )
        val result = try {
            runBlocking {
                resumeService.readResume(context, request)
            }
        } catch (e: Throwable) {
            runBlocking { resumeService.error(context, e) as ReadResumeResponse }
        }
        return result.toAPIGatewayV2HTTPResponse()

    }
}

class UpdateResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val request = input.toTransportModel<UpdateResumeRequest>()
        val context = ResumeContext(
            startTime = Instant.now()
        )
        val result = try {
            runBlocking {
                resumeService.updateResume(context, request)
            }
        } catch (e: Throwable) {
            runBlocking { resumeService.error(context, e) as UpdateResumeResponse }
        }
        return result.toAPIGatewayV2HTTPResponse()

    }
}

class DeleteResumeHandler: RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val request = input.toTransportModel<DeleteResumeRequest>()
        val context = ResumeContext(
            startTime = Instant.now()
        )
        val result = try {
            runBlocking {
                resumeService.deleteResume(context, request)
            }
        } catch (e: Throwable) {
            runBlocking { resumeService.error(context, e) as DeleteResumeResponse }
        }
        return result.toAPIGatewayV2HTTPResponse()

    }
}