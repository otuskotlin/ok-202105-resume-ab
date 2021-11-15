package ru.otus.otuskotlin.resume.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config)
    consumer.run()
}