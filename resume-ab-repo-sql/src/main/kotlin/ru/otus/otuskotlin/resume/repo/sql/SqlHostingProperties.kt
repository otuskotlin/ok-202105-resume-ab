package ru.otus.otuskotlin.resume.repo.sql

import java.io.File
import java.io.FileInputStream
import java.lang.IllegalArgumentException
import java.util.*

object SqlHostingProperties {
    const val SQL_URL = "jdbcUrl"
    const val SQL_DROP_DB = "ok.resume.sql_drop_db"
    const val SQL_FAST_MIGRATION = "ok.resume.sql_fast_migration"
}

fun loadFromFile(propertyFileName: String): Properties {
    val propFile = File(propertyFileName)
    val fileStream = if (propFile.isFile) {
        FileInputStream(propFile)
    } else {
        SqlHostingProperties::class.java.classLoader.getResourceAsStream(propertyFileName)
    }
    return if(fileStream != null) {
        Properties().apply { load(fileStream) }
    } else {
        throw IllegalArgumentException("Cannot find property file: $propertyFileName")
    }
}

fun Properties.property(key: String): String? = getProperty(key, null)

fun Properties.withoutCustom() = Properties().also { props ->
    filterNot { it.key.toString().startsWith("ok.resume.") }.forEach {
        props[it.key] = it.value
    }
}