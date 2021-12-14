val ktorVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

repositories {
    mavenCentral()
}

fun DependencyHandler.ktor(module: String, version: String = ktorVersion): String {
    return "io.ktor:ktor-$module:$version"
}

dependencies {
    implementation(ktor("server-core"))
    implementation(ktor("server-netty"))
    implementation(ktor("jackson"))
    implementation(ktor("websockets"))
    implementation(ktor("auth"))
    implementation(ktor("auth-jwt"))

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(ktor("server-test-host"))
    testImplementation(kotlin("test-junit"))

    //transport
    implementation(project(":common"))
    implementation(project(":resume-ab-transport-main-openapi"))
    implementation(project(":resume-ab-transport-mapping-openapi"))
    //service
    implementation(project(":resume-ab-service-openapi"))
    //logics
    implementation(project(":resume-ab-logics"))
    //stubs
    implementation(project(":resume-ab-stubs"))
    //DB
    implementation(project(":resume-ab-repo-inmemory"))
    //Logging
    implementation(project(":resume-ab-logging"))
}