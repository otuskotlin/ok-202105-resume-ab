package ru.otus.otuskotlin.resume.repo.sql

import org.testcontainers.containers.PostgreSQLContainer
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel
import java.time.Duration

class PostgresContainer: PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASSWORD = "postgrespass"
    private const val SCHEMA = "resume"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASSWORD)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(initObjects: Collection<ResumeModel> = emptyList()): RepoResumeSQL {
        return RepoResumeSQL(url, USER, PASSWORD, SCHEMA, initObjects)
    }
}