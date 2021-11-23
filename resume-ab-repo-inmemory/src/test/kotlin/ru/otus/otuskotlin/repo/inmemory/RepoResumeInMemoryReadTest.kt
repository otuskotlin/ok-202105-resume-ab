package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.reo.inmemory.RepoResumeInMemory
import ru.otus.otuskotlin.resume.repo.RepoResumeReadTest

class RepoResumeInMemoryReadTest: RepoResumeReadTest() {
    override val repo: IRepoResume = RepoResumeInMemory(
        initObjects = initObjects
    )
}