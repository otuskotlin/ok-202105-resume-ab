rootProject.name = "resume"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.openapi.generator") version openApiVersion
    }
}

include("common-kmp")
include("common")
include("resume-ab-transport-main-openapi")
include("resume-ab-transport-main-mp")
include("resume-ab-transport-mapping-openapi")
