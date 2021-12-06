package ru.otus.otuskotlin.resume.repo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import org.testcontainers.containers.CassandraContainer
import ru.otus.otuskotlin.resume.backend.common.models.PermissionModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeVisibilityModel
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.repo.*
import java.net.InetSocketAddress

class RepoResumeCassandraCreateTest : RepoResumeCreateTest() {
    override val repo: IRepoResume = TestCompanion.repository(initObjects, "ks_create")
}

class RepoResumeCassandraDeleteTest : RepoResumeDeleteTest() {
    override val repo: IRepoResume = TestCompanion.repository(initObjects, "ks_delete")
}

class RepoResumeCassandraReadTest : RepoResumeReadTest() {
    override val repo: IRepoResume = TestCompanion.repository(initObjects, "ks_read")
}

class RepoResumeCassandraUpdateTest : RepoResumeUpdateTest() {
    override val repo: IRepoResume = TestCompanion.repository(initObjects, "ks_update")
}

class TestCasandraContainer : CassandraContainer<TestCasandraContainer>("cassandra:3.11.2")

object TestCompanion {
    //    val keyspace = "data"
    val container by lazy { TestCasandraContainer().apply { start() } }

    val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {
            register(EnumNameCodec(ResumeVisibilityModel::class.java))
            register(EnumNameCodec(PermissionModel::class.java))
        }
    }

    fun createSchema(keyspace: String) {
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(ResumeCassandraDTO.table(keyspace, ResumeCassandraDTO.TABLE_NAME))
    }

    val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .withCodecRegistry(codecRegistry)
            .build()
    }

    val mapper by lazy { CassandraMapper.builder(session).build() }

    fun repository(initObjects: List<ResumeModel>, keyspace: String): RepoResumeCassandra {
        createSchema(keyspace)
        val dao = mapper.resumeDao(keyspace, ResumeCassandraDTO.TABLE_NAME)
        CompletableFutures
            .allDone(initObjects.map { dao.create(ResumeCassandraDTO(it)) })
            .toCompletableFuture()
            .get()

        return RepoResumeCassandra(dao)
    }
}
