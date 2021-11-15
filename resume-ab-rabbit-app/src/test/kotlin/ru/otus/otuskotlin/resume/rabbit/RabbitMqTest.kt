package ru.otus.otuskotlin.resume.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.testcontainers.containers.RabbitMQContainer
import resume.stubs.Ivan
import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.openapi.models.BaseDebugRequest
import ru.otus.otuskotlin.resume.openapi.models.CreatableResume
import ru.otus.otuskotlin.resume.openapi.models.CreateResumeRequest
import ru.otus.otuskotlin.resume.openapi.models.CreateResumeResponse
import ru.otus.otuskotlin.resume.service.services.ResumeService
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RabbitMqTest {
    companion object {
        const val keyIn = "key-in"
        const val keyOut = "key-Out"
        const val exchange = "test-exchange"
        const val queue = "test-queue"
        private val container by lazy {

            RabbitMQContainer("rabbitmq:latest").apply {
                withUser("guest", "guest")
                start()
            }
        }
        private val rabbitMqTestPort: Int by lazy {
            container.getMappedPort(5672)
        }
        val config by lazy {
            RabbitConfig(
                port = rabbitMqTestPort
            )
        }
        private val crud = ResumeCrud()
        private val service = ResumeService(crud)
        private val processor by lazy {
            RabbitDirectProcessor(
                config = config,
                keyIn = keyIn,
                keyOut = keyOut,
                exchange = exchange,
                queue = queue,
                service = service,
                consumerTag = "test-tag"
            )
        }
        val controller by lazy {
            RabbitController(
                processors = setOf(processor)
            )
        }
        val mapper = ObjectMapper()
    }

    @BeforeTest
    fun init() {
        controller.start()
    }

    @Test
    fun resumeCreateTest() {
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.getBody(), Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, mapper.writeValueAsBytes(resumeCreate))

                runBlocking {
                    withTimeoutOrNull(250L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = mapper.readValue(responseJson, CreateResumeResponse::class.java)
                val expected = Ivan.getModel()
                assertEquals(expected.firstName, response.createdResume?.firstName)
                assertEquals(expected.lastName, response.createdResume?.lastName)
                assertEquals(expected.middleName, response.createdResume?.middleName)
            }
        }
    }

    private val resumeCreate = with(Ivan.getModel()){
        CreateResumeRequest(
            createResume = CreatableResume(
                firstName = firstName,
                lastName = lastName,
                middleName = middleName
            ),
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.STUB,
                stubCase = BaseDebugRequest.StubCase.SUCCESS,
            )
        )
    }
}