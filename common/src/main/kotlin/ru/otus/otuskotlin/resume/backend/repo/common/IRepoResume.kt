package ru.otus.otuskotlin.resume.backend.repo.common

interface IRepoResume {
    suspend fun create(rq: DbResumeModelRequest): DbResumeResponse
    suspend fun read(rq: DbResumeIdRequest): DbResumeResponse
    suspend fun update(rq: DbResumeModelRequest): DbResumeResponse
    suspend fun delete(rq: DbResumeIdRequest): DbResumeResponse

    object NONE: IRepoResume {
        override suspend fun create(rq: DbResumeModelRequest): DbResumeResponse {
            TODO("Not yet implemented")
        }

        override suspend fun read(rq: DbResumeIdRequest): DbResumeResponse {
            TODO("Not yet implemented")
        }

        override suspend fun update(rq: DbResumeModelRequest): DbResumeResponse {
            TODO("Not yet implemented")
        }

        override suspend fun delete(rq: DbResumeIdRequest): DbResumeResponse {
            TODO("Not yet implemented")
        }
    }
}