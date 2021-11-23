package ru.otus.otuskotlin.resume.logics.workers

import ru.otus.otuskotlin.resume.backend.common.context.ResumeContext
import ru.otus.otuskotlin.resume.backend.common.models.ResumeStubCase
import ru.otus.otuskotlin.resume.backend.common.models.WorkMode
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.cor.ICorChainDsl
import ru.otus.otuskotlin.resume.cor.handlers.worker

internal fun ICorChainDsl<ResumeContext>.chooseDb(title: String) = worker {
    this.title = title
    description = """
         Here we choose either prod or test DB repository. 
        In case of STUB request here we use empty repo and set stubCase=SUCCESS if unset
    """.trimIndent()

    handle {
        resumeRepo = when(workMode) {
            WorkMode.PROD -> config.repoProd
            WorkMode.TEST -> config.repoTest
            WorkMode.STUB -> {
                if(stubCase == ResumeStubCase.NONE) {
                    stubCase = ResumeStubCase.SUCCESS
                }
                IRepoResume.NONE
            }
        }
    }
}