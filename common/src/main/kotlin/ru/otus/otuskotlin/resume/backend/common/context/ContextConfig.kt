package ru.otus.otuskotlin.resume.backend.common.context

import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume

data class ContextConfig(
    val repoProd: IRepoResume = IRepoResume.NONE,
    val repoTest: IRepoResume = IRepoResume.NONE,
    )