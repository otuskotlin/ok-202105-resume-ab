package ru.otus.otuskotlin.resume.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.*
import java.util.concurrent.CompletionStage

@Dao
interface ResumeCassandraDAO {
    @Insert
    fun create(dto: ResumeCassandraDTO): CompletionStage<Unit>

    @Select
    fun read(id: String): CompletionStage<ResumeCassandraDTO?>

    @Update(ifExists = true)
    fun update(dto: ResumeCassandraDTO): CompletionStage<Unit>

    @Delete
    fun delete(dto: ResumeCassandraDTO): CompletionStage<Unit>
}