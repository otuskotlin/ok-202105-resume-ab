package ru.otus.otuskotlin.resume.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.openapi.models.BaseMessage
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class AppKafkaConsumer(private val config: AppKafkaConfig) {
    private val consumer = config.kafkaConsumer
    private val producer = config.kafkaProducer
    private val service = config.service
    private val mapper = ObjectMapper()
    private val process = AtomicBoolean(true)

    fun run() = runBlocking {
        try {
            consumer.subscribe(listOf(config.kafkaTopicIn))
            while (process.get()) {
                val ctx = ResumeContext(
                    startTime = Instant.now()
                )
                try {
                    val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
                    records.forEach { record: ConsumerRecord<String, String> ->
                        val request = withContext(Dispatchers.IO) {
                            mapper.readValue(record.value(), BaseMessage::class.java)
                        }
                        sendResponse(service.handleResume(ctx, request))
                    }
                } catch (e: Throwable) {
                    sendResponse(service.error(ctx, e))
                }
            }
        } catch (ex: WakeupException) {
        } catch (ex: RuntimeException) {
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private suspend fun sendResponse(response: BaseMessage) {
        val json = withContext(Dispatchers.IO) { mapper.writeValueAsString(response) }
        val responseRecord = ProducerRecord(
            config.kafkaTopicOut,
            UUID.randomUUID().toString(),
            json
        )
        producer.send(responseRecord)
    }

    fun stop() {
        process.set(false)
    }
}
