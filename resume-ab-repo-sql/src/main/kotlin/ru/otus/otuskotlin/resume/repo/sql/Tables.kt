package ru.otus.otuskotlin.resume.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import ru.otus.otuskotlin.resume.backend.common.models.*

object ResumeTable: Table("Resume") {
    val id = uuid("id").autoGenerate().uniqueIndex()
    val lastName =varchar("last_name", 128)
    val firstName = varchar("first_name", 128)
    val middleName = varchar("middle_name", 128)
    val age = varchar("age", 4)
    val birthDate = varchar("age", 16)
    val gender = enumeration("gender", ResumeGenderModel::class)
    val ownerId = reference("owner_id", UsersTable.id).index()
    val visibility = enumeration("visibility", ResumeVisibilityModel::class)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = ResumeModel(
        id = ResumeIdModel(res[id]),
        lastName = res[lastName],
                firstName = res[firstName],
                middleName = res[middleName],
                age = res[age],
                birthDate = res[birthDate],
                gender = res[gender],
                ownerId = OwnerIdModel(res[ownerId]),
                visibility = res[visibility]
    )

    fun from(res: ResultRow) = ResumeModel(
        id = ResumeIdModel(res[id]),
        lastName = res[lastName],
        firstName = res[firstName],
        middleName = res[middleName],
        age = res[age],
        birthDate = res[birthDate],
        gender = res[gender],
        ownerId = OwnerIdModel(res[ownerId]),
        visibility = res[visibility]
    )
}

object UsersTable: Table("Users") {
    val id = uuid("id").autoGenerate().uniqueIndex()
    val name = varchar("name", 128)
    override val primaryKey = PrimaryKey(ResumeTable.id)
}