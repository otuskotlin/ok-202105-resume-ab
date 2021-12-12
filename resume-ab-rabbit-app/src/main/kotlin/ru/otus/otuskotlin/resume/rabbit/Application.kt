package ru.otus.otuskotlin.resume.rabbit

import ru.otus.otuskotlin.resume.logics.ResumeCrud
import ru.otus.otuskotlin.resume.service.services.ResumeService

fun main() {
    val config = RabbitConfig()
    val processor by lazy {
        RabbitDirectProcessor(
            config = config,
            keyIn = "in",
            keyOut = "out",
            exchange = "test-exchange",
            queue = "test=queue",
            service = config.service,
            consumerTag = "test-tag"
        )
    }
    val controller by lazy {
        RabbitController(
            processors = setOf(processor)
        )
    }
    controller.start()
}
