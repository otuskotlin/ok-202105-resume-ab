package ru.otus.otuskotlin.resume.rabbit

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.util.concurrent.Executors

class RabbitController(
    private val processors: Set<RabbitDirectProcessor>
) {
    private val scope = CoroutineScope(
        Executors.newSingleThreadExecutor()
            .asCoroutineDispatcher() + CoroutineName("thread-rabbit-controller")
    )

    fun start() = scope.launch {
        processors.forEach {
            launch (
                Executors.newSingleThreadExecutor()
                    .asCoroutineDispatcher() + CoroutineName("thread-${it.consumerTag}")) {
                try {
                    it.process()
                } catch (e: RuntimeException) {
                    e.printStackTrace()
                }
            }
        }
    }
}