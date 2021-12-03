package ru.otus.otuskotlin.resume.repo.sql

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.resume.backend.common.models.CommonErrorModel
import ru.otus.otuskotlin.resume.backend.common.models.OwnerIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeIdModel
import ru.otus.otuskotlin.resume.backend.common.models.ResumeModel
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeIdRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeModelRequest
import ru.otus.otuskotlin.resume.backend.repo.common.DbResumeResponse
import ru.otus.otuskotlin.resume.backend.repo.common.IRepoResume
import java.sql.SQLException

class RepoResumeSQL(
    url: String = "jdbc:postgresql://localhost:5432/resumedevdb",
    user: String = "postgres",
    password: String = "postgrespass",
    schema: String = "resume",
    initObjects: Collection<ResumeModel> = emptyList()
) : IRepoResume {
    private val db by lazy { SqlConnector(url, user, password, schema).connect(ResumeTable, UsersTable) }

    init {
        runBlocking {
            initObjects.forEach {
                save(it)
            }
        }
    }

    /**
     * Transaction wrapper to safely handle caught exception and throw all sql-like exceptions. Also remove lot's of duplication code
     */
    private fun <T> safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T {
        return try {
            transaction(db, statement)
        } catch (e: SQLException) {
            throw e
        } catch (e: Throwable) {
            return handleException(e)
        }
    }

    private suspend fun save(item: ResumeModel): DbResumeResponse {
        return safeTransaction({
            val realOwnerId = UsersTable.insertIgnore {
                if (item.ownerId != OwnerIdModel.NONE) {
                    it[id] = item.ownerId.asUUID()
                }
                it[name] = item.ownerId.asUUID().toString()
            } get UsersTable.id

            val res = ResumeTable.insert {
                if (item.id != ResumeIdModel.NONE) {
                    it[id] = item.id.asUUID()
                }
                it[firstName] = item.firstName
                it[lastName] = item.lastName
                it[middleName] = item.middleName
                it[age] = item.age
                it[birthDate] = item.birthDate
                it[gender] = item.gender
                it[ownerId] = realOwnerId
                it[visibility] = item.visibility
            }

            DbResumeResponse(ResumeTable.from(res), true, errors = emptyList())
        }, {
            DbResumeResponse(
                result = null,
                isSuccess = true,
                errors = listOf(CommonErrorModel(message = localizedMessage))
            )
        })
    }

    override suspend fun create(rq: DbResumeModelRequest): DbResumeResponse {
        return save(rq.resume)
    }

    override suspend fun read(rq: DbResumeIdRequest): DbResumeResponse {
        return safeTransaction({
            val result = (ResumeTable innerJoin UsersTable).select { ResumeTable.id.eq(rq.id.asUUID()) }.single()

            DbResumeResponse(ResumeTable.from(result), true, emptyList())
        }, {
            val error = when (this) {
                is NoSuchElementException -> CommonErrorModel(field = "id", message = "Not Found")
                is IllegalArgumentException -> CommonErrorModel(message = "More than one element found with the same id")
                else -> CommonErrorModel(message = localizedMessage)
            }
            DbResumeResponse(result = null, isSuccess = false, errors = listOf(error))
        })
    }

    override suspend fun update(rq: DbResumeModelRequest): DbResumeResponse {
        val resume = rq.resume
        return safeTransaction({
            UsersTable.insertIgnore {
                if (resume.ownerId != OwnerIdModel.NONE) {
                    it[id] = resume.ownerId.asUUID()
                }
                it[name] = resume.ownerId.asUUID().toString()
            }

            UsersTable.update ({ UsersTable.id.eq(resume.ownerId.asUUID()) }) {
                it[name] = resume.ownerId.asUUID().toString()
            }
            ResumeTable.update {
                it[firstName] = resume.firstName
                it[lastName] = resume.lastName
                it[middleName] = resume.middleName
                it[age] = resume.age
                it[birthDate] = resume.birthDate
                it[gender] = resume.gender
                it[ownerId] = resume.ownerId.asUUID()
                it[visibility] = resume.visibility
            }

            val result = ResumeTable.select{ResumeTable.id.eq(resume.id.asUUID())}.single()
            DbResumeResponse(ResumeTable.from(result), true, errors = emptyList())
        }, {
            DbResumeResponse(
                result = null,
                isSuccess = true,
                errors = listOf(CommonErrorModel(message = localizedMessage))
            )
        })
    }

    override suspend fun delete(rq: DbResumeIdRequest): DbResumeResponse {
        return safeTransaction({
            val result = ResumeTable.select { ResumeTable.id.eq(rq.id.asUUID()) }.single()
            ResumeTable.deleteWhere { ResumeTable.id eq rq.id.asUUID() }

            DbResumeResponse(result = ResumeTable.from(result), isSuccess = true, errors = emptyList())
        }, {
            DbResumeResponse(
                isSuccess = false, result = null, errors = listOf(CommonErrorModel(field = "id", message = "Not found"))
            )
        })
    }
}