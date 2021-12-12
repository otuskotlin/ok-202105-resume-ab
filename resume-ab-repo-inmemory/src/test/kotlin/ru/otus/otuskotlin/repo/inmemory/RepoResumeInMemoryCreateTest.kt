package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.repo.inmemory.RepoResumeInMemory
import ru.otus.otuskotlin.resume.repo.RepoResumeCreateTest

class RepoResumeInMemoryCreateTest: RepoResumeCreateTest() {
    override val repo: IRepoResume = RepoResumeInMemory(
        initObjects = initObjects
    )
}