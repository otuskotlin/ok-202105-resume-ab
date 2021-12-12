package ru.otus.otuskotlin.resume.repo.sql

import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.repo.RepoResumeCreateTest
import ru.otus.otuskotlin.resume.repo.RepoResumeDeleteTest
import ru.otus.otuskotlin.resume.repo.RepoResumeReadTest
import ru.otus.otuskotlin.resume.repo.RepoResumeUpdateTest

class RepoResumeSqlCreateTest: RepoResumeCreateTest() {
    override val repo: IRepoResume = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoResumeSqlReadTest: RepoResumeReadTest() {
    override val repo: IRepoResume = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoResumeSqlUpdateTest: RepoResumeUpdateTest() {
    override val repo: IRepoResume = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoResumeSqlDeleteTest: RepoResumeDeleteTest() {
    override val repo: IRepoResume = SqlTestCompanion.repoUnderTestContainer(initObjects)
}