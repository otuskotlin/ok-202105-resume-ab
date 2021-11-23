package ru.otus.otuskotlin.resume.repo.inmemory

import kotlinx.coroutines.runBlocking
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeResponse
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import ru.otus.otuskotlin.resume.repo.inmemory.models.ResumeRow
import java.time.Duration
import java.util.*

class RepoResumeInMemory(
    private val initObjects: List<ResumeModel> = listOf(),
    private val ttl: Duration = Duration.ofMinutes(1)
) : IRepoResume {
    private val cache: Cache<String, ResumeRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "resume-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    ResumeRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }

    init {
        runBlocking { initObjects.forEach { save(it) } }
    }

    private suspend fun save(item: ResumeModel): DbResumeResponse {
        val row = ResumeRow(item)
        if (row.id == null) {
            return DbResumeResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                )
            )
        }
        cache.put(row.id, row)
        return DbResumeResponse(
            result = row.toInternal(),
            isSuccess = true,
            errors = emptyList()
        )
    }

    override suspend fun create(rq: DbResumeModelRequest): DbResumeResponse =
        save(rq.resume.copy(id = ResumeIdModel(UUID.randomUUID().toString())))

    override suspend fun read(rq: DbResumeIdRequest): DbResumeResponse = cache.get(rq.id.asString())?.let {
        DbResumeResponse(
            result = it.toInternal(),
            isSuccess = true,
            errors = emptyList()
        )
    } ?: DbResumeResponse(
        result = null,
        isSuccess = false,
        errors = listOf(
            CommonErrorModel(field = "id", message = "Not Found")
        )
    )

    override suspend fun update(rq: DbResumeModelRequest): DbResumeResponse {
        val key = rq.resume.id.takeIf { it != ResumeIdModel.NONE }?.asString()
            ?: return DbResumeResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(field = "id", message = "Id must not be null or blank")
                )
            )

        return if (cache.containsKey(key)) {
            save(rq.resume)
            DbResumeResponse(
                result = rq.resume,
                isSuccess = true,
                errors = emptyList()
            )
        } else {
            DbResumeResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id", message = "Not Found"
                    )
                )
            )
        }
    }

    override suspend fun delete(rq: DbResumeIdRequest): DbResumeResponse {
        val key = rq.id.takeIf { it != ResumeIdModel.NONE }?.asString()
            ?: return DbResumeResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(field = "id", message = "Id must not be null or blank")
                )
            )
        val row = cache.get(key) ?: return DbResumeResponse(
            result = null,
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(field = "id", message = "Not Found")
            ))
        cache.remove(key)

        return DbResumeResponse(
            isSuccess = true,
            result = row.toInternal(),
            errors = emptyList()
        )
    }
}