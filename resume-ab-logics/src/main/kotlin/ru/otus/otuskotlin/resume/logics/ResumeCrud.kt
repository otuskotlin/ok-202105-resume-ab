package ru.otus.otuskotlin.resume.logics

import ru.otus.otuskotlin.resume.backend.common.context.ContextConfig
import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.logics.chains.*

/**
 * Класс-фасад, содержащий все методы бизнес-логики
 */
class ResumeCrud (val config: ContextConfig = ContextConfig()) {
    suspend fun init(context: ResumeContext) {
        ResumeInit.exec(context.initSettings())
    }
    suspend fun create(context: ResumeContext) {
        ResumeCreate.exec(context.initSettings())
    }
    suspend fun read(context: ResumeContext) {
        ResumeRead.exec(context.initSettings())
    }
    suspend fun update(context: ResumeContext) {
        ResumeUpdate.exec(context.initSettings())
    }
    suspend fun delete (context: ResumeContext) {
        ResumeDelete.exec(context.initSettings())
    }

    // Метод для установки параметров чейна в контекст, параметры передаются в конструкторе класса
    private fun ResumeContext.initSettings() = apply { config = this@ResumeCrud.config }
}