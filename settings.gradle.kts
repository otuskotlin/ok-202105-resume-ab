rootProject.name = "resume"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.openapi.generator") version openApiVersion

        // spring
        val springBootVersion: String by settings
        val springDependencyVersion: String by settings
        val springPluginVersion: String by settings

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion
        kotlin("plugin.spring") version springPluginVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion

    }
}

include("common-kmp")
include("common")
include("resume-ab-transport-main-openapi")
include("resume-ab-transport-main-mp")
include("resume-ab-transport-mapping-openapi")
include("resume-ab-spring-app")
include("resume-ab-spring-app")
include("resume-ab-stubs")
include("resume-ab-ktor-app")
include("resume-ab-aws-app")
include("resume-ab-service-openapi")
include("resume-ab-common-cor")
include("resume-ab-logics")
include("resume-ab-validation")
include("resume-ab-rabbit-app")
include("resume-ab-kafka")
