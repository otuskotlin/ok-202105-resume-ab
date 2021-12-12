package ru.otus.otuskotlin.resume.repo.sql

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.DEFAULT_ISOLATION_LEVEL
import org.jetbrains.exposed.sql.transactions.transaction

class SqlConnector (
    private val url: String = "dbc:postgresql://localhost:5432/resumedevdb",
    private val driver: String = "org.postgresql.Driver",
    private val user: String = "postgres",
    private val password: String = "postgrespass",
    private val schema: String = "resume",
    private val databaseConfig: DatabaseConfig = DatabaseConfig { defaultIsolationLevel = DEFAULT_ISOLATION_LEVEL}
) {
    fun connect(vararg  tables: Table): Database {
        transaction(Database.connect(url,driver, user, password, databaseConfig = databaseConfig)) {
            connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS $schema", false).executeUpdate()
        }
        val connect = Database.connect(
            url, driver, user, password,
            databaseConfig = databaseConfig,
            setupConnection = { connection ->
                connection.transactionIsolation = DEFAULT_ISOLATION_LEVEL
                connection.schema = schema
            }
        )

        transaction(connect) {
            if (System.getenv("ok.resume.sql_drop_db")?.toBoolean() == true) {
                SchemaUtils.drop(*tables, inBatch = true)
                SchemaUtils.create(*tables, inBatch = true)
            } else {
                SchemaUtils.createMissingTablesAndColumns(*tables, inBatch = true)
            }
        }

        return connect
    }
}