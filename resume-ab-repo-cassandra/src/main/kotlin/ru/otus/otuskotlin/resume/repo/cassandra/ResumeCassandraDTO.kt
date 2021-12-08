package ru.otus.otuskotlin.resume.repo.cassandra

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.otus.otuskotlin.resume.backend.common.models.*

@Entity
class ResumeCassandraDTO (
    @field:CqlName(COLUMN_ID)
    @PartitionKey
    var id: String? = null,
    @field:CqlName(COLUMN_FIRST_NAME)
    var firstName: String? = null,
    @field:CqlName(COLUMN_LAST_NAME)
    var lastName: String? = null,
    @field:CqlName(COLUMN_MIDDLE_NAME)
    var middleName: String? = null,
    @field:CqlName(COLUMN_AGE)
    var age: String? = null,
    @field:CqlName(COLUMN_BIRTH_DATE)
    var birthDate: String? = null,
    @field:CqlName(COLUMN_GENDER)
    var gender: ResumeGenderModel? = null,
    @field:CqlName(COLUMN_OWNER_ID)
    var ownerId: String? = null
) {
    constructor(resumeModel: ResumeModel) : this(
        id = resumeModel.id.takeIf { it != ResumeIdModel.NONE }?.asString(),
        firstName = resumeModel.firstName.takeIf { it.isNotBlank() },
        lastName = resumeModel.lastName.takeIf { it.isNotBlank()},
        middleName = resumeModel.middleName.takeIf { it.isNotBlank() },
        age = resumeModel.age.takeIf { it.isNotBlank() },
        birthDate = resumeModel.birthDate.takeIf { it.isNotBlank() },
        gender = resumeModel.gender.takeIf { it != ResumeGenderModel.NONE },
        ownerId = resumeModel.ownerId.takeIf { it != OwnerIdModel.NONE }?.asString(),
        )

    fun toResumeModel(): ResumeModel = ResumeModel(
        id = id?.let { ResumeIdModel(it) } ?: ResumeIdModel.NONE,
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        middleName = middleName ?: "",
        age = age ?: "",
        birthDate = birthDate ?: "",
        gender = gender ?: ResumeGenderModel.NONE,
        ownerId = ownerId?.let { OwnerIdModel(it) } ?: OwnerIdModel.NONE,
        )

    companion object {
        const val TABLE_NAME = "resume"
        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME= "first_name"
        const val COLUMN_LAST_NAME= "last_name"
        const val COLUMN_MIDDLE_NAME= "middle_name"
        const val COLUMN_AGE= "age"
        const val COLUMN_BIRTH_DATE= "birth_date"
        const val COLUMN_GENDER= "gender"
        const val COLUMN_OWNER_ID= "owner_id"
        const val COLUMN_VISIBILITY= "visibility"
        const val COLUMN_PERMISSIONS= "permissions"

        fun table(keyspace: String, tableName: String) = SchemaBuilder
            .createTable(keyspace, tableName)
            .ifNotExists()
            .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
            .withColumn(COLUMN_FIRST_NAME, DataTypes.TEXT)
            .withColumn(COLUMN_LAST_NAME, DataTypes.TEXT)
            .withColumn(COLUMN_MIDDLE_NAME, DataTypes.TEXT)
            .withColumn(COLUMN_AGE, DataTypes.TEXT)
            .withColumn(COLUMN_BIRTH_DATE, DataTypes.TEXT)
            .withColumn(COLUMN_GENDER, DataTypes.TEXT)
            .withColumn(COLUMN_OWNER_ID, DataTypes.TEXT)
            .build()
    }
}