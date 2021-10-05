package ru.otus.otuskotlin.validation.cor.worker

import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker
import ru.otus.otuskotlin.validation.cor.ValidationBuilder

fun <C> ICorChainDsl<C>.validation(block: ValidationBuilder<C>.() -> Unit) {
    worker {
        this.title = "Валидация"
        description = "Валидация логики"
        handle {
            ValidationBuilder<C>().apply(block).build().exec(this)
        }
    }
}