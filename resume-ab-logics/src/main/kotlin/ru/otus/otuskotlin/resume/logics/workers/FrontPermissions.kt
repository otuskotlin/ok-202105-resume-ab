//package ru.otus.otuskotlin.resume.logics.workers
//
//import ru.otus.otuskotlin.resume.backend.common.context.CorStatus
//import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
//import ru.otus.otuskotlin.resume.backend.common.models.PermissionModel
//import ru.otus.otuskotlin.resume.backend.common.models.ResumeUserGroups
//import ru.otus.otuskotlin.resume.cor.ICorChainDsl
//import ru.otus.otuskotlin.resume.cor.handlers.chain
//import ru.otus.otuskotlin.resume.cor.handlers.worker
//
//fun ICorChainDsl<ResumeContext>.frontPermissions(title: String) = chain {
//    this.title = title
//    description = "Вычисление разрешений пользователей для фронтенда"
//
//    on { status == CorStatus.RUNNING }
//
//    worker {
//        this.title = "Разрешения для собственного резюме"
//        description = this.title
//        on { responseResume.ownerId == principal.id }
//        handle {
//            val permAdd: Set<PermissionModel> = principal.groups.map {
//                when(it) {
//                    ResumeUserGroups.USER -> setOf(
//                        PermissionModel.READ,
//                        PermissionModel.UPDATE,
//                        PermissionModel.DELETE,
//                    )
//                    ResumeUserGroups.ADMIN -> setOf()
//                    ResumeUserGroups.TEST -> setOf()
//                }
//            }.flatten().toSet()
//            responseResume.permissions.addAll(permAdd)
//        }
//    }
//
//    worker {
//        this.title = "Разрешения для администратора"
//        description = this.title
//    }
//}