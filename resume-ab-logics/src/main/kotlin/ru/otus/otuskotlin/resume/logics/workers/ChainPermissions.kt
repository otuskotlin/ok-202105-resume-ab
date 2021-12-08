//package ru.otus.otuskotlin.resume.logics.workers
//
//import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
//import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
//import ru.otus.otuskotlin.resume.backend.common.models.ResumeUserGroups
//import ru.otus.otuskotlin.resume.backend.common.models.ResumeUserPermissions
//import ru.otus.otuskotlin.resume.cor.ICorChainDsl
//import ru.otus.otuskotlin.resume.cor.handlers.worker
//
//fun ICorChainDsl<ResumeContext>.chainPermissions(title: String) = worker<ResumeContext> {
//    this.title = title
//    description = "Вычисление прав доступа для групп пользователей"
//
//    on { status == CorStatus.RUNNING}
//
//    handle {
//        val permAdd: Set<ResumeUserPermissions> = principal.groups.map {
//            when(it) {
//                ResumeUserGroups.USER -> setOf(
//                    ResumeUserPermissions.CREATE_OWN,
//                    ResumeUserPermissions.READ_OWN,
//                    ResumeUserPermissions.UPDATE_OWN,
//                    ResumeUserPermissions.DELETE_OWN,
//                )
//                ResumeUserGroups.ADMIN -> setOf()
//                ResumeUserGroups.TEST -> setOf()
//            }
//        }.flatten().toSet()
//
//        chainPermissions.addAll(permAdd)
//        println("PRINCIPAL: $principal")
//        println("PERMISSIONS: $chainPermissions")
//    }
//}