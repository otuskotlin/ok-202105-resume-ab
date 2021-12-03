package ru.otus.otuskotlin.resume.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.resume.openapi.models.*
import java.util.*
import kotlin.test.assertEquals

class AppKafkaTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig(
            kafkaConsumer = consumer,
            kafkaProducer = producer,
        )
        val app = AppKafkaConsumer(config)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(config.kafkaTopicIn, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicIn,
                    PARTITION,
                    0L,
                    "test-1",
                    CreateResumeRequest(
                        requestId = "f62d0c1c-bccd-486f-9364-857581cd6282",
                        createResume = CreatableResume(
                            firstName = "Ivan",
                            lastName = "Ivanov",
                            middleName = "Ivanovich",
                            ownerId = "345",
                            visibility = ResumeVisibility.OWNER_ONLY,
                            birthDate = "2020-01-01",
                            age = "1",
                            gender = CreatableResume.Gender.MALE,
                        ),
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )
            println("================================================")
            println("================================================")
            println(consumer)
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
//        val startOffsets = HashMap<TopicPartition, Long>()
        val tp = TopicPartition(config.kafkaTopicIn, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = message.value().fromJson<CreateResumeResponse>()
        assertEquals("f62d0c1c-bccd-486f-9364-857581cd6282", result.requestId)
        assertEquals("Ivan", result.createdResume?.firstName)
    }

    companion object {
        const val PARTITION = 0
    }
}

private val om = ObjectMapper()
private fun BaseMessage.toJson(): String = om.writeValueAsString(this)
private inline fun <reified T : BaseMessage> String.fromJson() = om.readValue<T>(this, T::class.java)
