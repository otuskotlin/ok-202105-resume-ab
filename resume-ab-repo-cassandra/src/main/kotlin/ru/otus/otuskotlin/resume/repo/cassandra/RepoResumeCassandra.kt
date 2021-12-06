package ru.otus.otuskotlin.resume.repo.cassandra

import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeResponse
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import java.util.*

class RepoResumeCassandra(
    private val dao: ResumeCassandraDAO,
    private val timeoutMillis: Long = 30_000
) : IRepoResume {
    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun create(rq: DbResumeModelRequest): DbResumeResponse {
        val new = rq.resume.copy(id = ResumeIdModel(UUID.randomUUID().toString()))
        return try {
            withTimeout(timeoutMillis) { dao.create(ResumeCassandraDTO(new)).await()}
            DbResumeResponse(new)
        } catch (e: Exception) {
            log.error("Failed to create", e)
            DbResumeResponse(CommonErrorModel(e))
        }
    }

    override suspend fun read(rq: DbResumeIdRequest): DbResumeResponse {
        return if (rq.id == ResumeIdModel.NONE) {
            DbResumeResponse(CommonErrorModel(field = "id", message = "Id is empty"))
        } else try {
            val found = withTimeout(timeoutMillis) { dao.read(rq.id.asString()).await()}
            if(found!=null) DbResumeResponse(found.toResumeModel())
            else DbResumeResponse(CommonErrorModel(field = "id", message = "Not Found"))
        } catch (e: Exception) {
            log.error("Failed to read", e)
            DbResumeResponse(CommonErrorModel(e))
        }
    }

    override suspend fun update(rq: DbResumeModelRequest): DbResumeResponse {
        return if (rq.resume.id == ResumeIdModel.NONE) {
            DbResumeResponse(CommonErrorModel(field = "resume.id", message = "Id is empty"))
        } else try {
            val updated = withTimeout(timeoutMillis) {
                dao.update(ResumeCassandraDTO(rq.resume)).await()
                dao.read(rq.resume.id.asString()).await()
            }
            if(updated!=null) DbResumeResponse(updated.toResumeModel())
            else DbResumeResponse(CommonErrorModel(field = "id", message = "Not Found"))
        } catch (e: Exception) {
            log.error("Failed to read", e)
            DbResumeResponse(CommonErrorModel(e))
        }
    }

    override suspend fun delete(rq: DbResumeIdRequest): DbResumeResponse {
        return if (rq.id == ResumeIdModel.NONE) {
            DbResumeResponse(CommonErrorModel(field = "resume.id", message = "Id is empty"))
        } else try {
            val deleted = withTimeout(timeoutMillis) {
                dao.read(rq.id.asString()).await()?.also {dao.delete(it).await()}
            }
            if(deleted!=null) DbResumeResponse(deleted.toResumeModel())
            else DbResumeResponse(CommonErrorModel(field = "id", message = "Not Found"))
        } catch (e: Exception) {
            log.error("Failed to read", e)
            DbResumeResponse(CommonErrorModel(e))
        }
    }
}