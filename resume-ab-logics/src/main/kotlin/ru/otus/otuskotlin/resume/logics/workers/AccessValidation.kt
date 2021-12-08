//package ru.otus.otuskotlin.resume.logics.workers
//
//import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
//import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
//import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
//import ru.otus.otuskotlin.resume.cor.ICorChainDsl
//import ru.otus.otuskotlin.resume.cor.handlers.chain
//import ru.otus.otuskotlin.resume.cor.handlers.worker
//import ru.otus.otuskotlin.resume.logics.chains.helpers.AccessTableConditions
//import ru.otus.otuskotlin.resume.logics.chains.helpers.accessTable
//import ru.otus.otuskotlin.resume.logics.chains.helpers.resolveRelationsTo
//
//fun ICorChainDsl<ResumeContext>.accessValidation(title: String) = chain {
//    this.title = title
//    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
//    on { status == CorStatus.RUNNING }
//
//    worker("Вычисление отношения объявления к принципалу") {
//        dbResume.principalRelations = dbResume.resolveRelationsTo(principal)
//    }
//
//    worker("Вычисление доступа к объявлению") {
//        permitted = dbResume.principalRelations.flatMap { relation ->
//            chainPermissions.map { permission ->
//                AccessTableConditions(
//                    operation = operation,
//                    permission = permission,
//                    relation = relation,
//                )
//            }
//        }
//            .any {
//                accessTable[it] ?: false
//            }
//    }
//
//    worker {
//        this.title = "Валидация прав доступа"
//        description = "Проверка наличия прав для выполнения операции"
//        on {!permitted}
//        handle {
//            addError(
//                CommonErrorModel(message = "User is not allowed to this operation")
//            )
//        }
//    }
//}