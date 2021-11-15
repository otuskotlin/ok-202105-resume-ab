package ru.otus.otuskotlin.resume.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.transport.mapping.kmp.toInitResponse
import ru.otus.otuskotlin.resume.openapi.models.*
import ru.otus.otuskotlin.resume.service.services.ResumeService
import java.time.Instant

class RabbitDirectProcessor(
    config: RabbitConfig,
    consumerTag: String = "",
    private val keyIn: String,
    private val keyOut: String,
    private val exchange: String,
    private val queue: String,
    private val service: ResumeService,
): RabbitProcessorBase(config, consumerTag) {
    private val jacksonMapper = ObjectMapper()
    override fun Channel.getDeliveryCallback(): DeliverCallback {
        val channel = this
        return DeliverCallback { tag, message ->
            runBlocking {
                val context = ResumeContext(
                    startTime = Instant.now()
                )

                try {
                    when (val query = jacksonMapper.readValue(message.body, BaseMessage::class.java)) {
                        is InitResumeRequest -> {
                            val response = service.initResume(context, query)
                            jacksonMapper.writeValueAsBytes(response)
                        }
                        is CreateResumeRequest -> {
                            val response = service.createResume(context, query)
                            jacksonMapper.writeValueAsBytes(response)
                        }
                        is ReadResumeRequest -> {
                            val response = service.readResume(context, query)
                            jacksonMapper.writeValueAsBytes(response)
                        }
                        is UpdateResumeRequest -> {
                            val response = service.updateResume(context, query)
                            jacksonMapper.writeValueAsBytes(response)
                        }
                        is DeleteResumeRequest -> {
                            val response = service.deleteResume(context, query)
                            jacksonMapper.writeValueAsBytes(response)
                        }
                        else -> {
                             throw UnsupportedOperationException("Unsupported operation request")
                        }
                    }?.also {
                        channel.basicPublish(exchange, keyOut, null, it)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    context.status = CorStatus.ERROR
                    context.addError(e = e)
                    val response = context.toInitResponse()
                    jacksonMapper.writeValueAsBytes(response).also {
                        channel.basicPublish(exchange, keyOut, null, it)
                    }
                }
            }
        }
    }

    override fun Channel.getCancelCallback() = CancelCallback {
        println("[$it] was cancelled")
    }

    override fun Channel.listen(deliverCallback: DeliverCallback, cancelCallback: CancelCallback) {
        exchangeDeclare(exchange, "direct")
        queueDeclare(queue, false, false, false, null)
        queueBind(queue, exchange, keyIn)
        basicConsume(queue, true, consumerTag, deliverCallback, cancelCallback)
        while (isOpen){}
        println("Channel for [$consumerTag] was closed.")
    }
}