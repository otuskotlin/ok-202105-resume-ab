val ktorVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
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

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(ktor("server-test-host"))
    testImplementation(kotlin("test-junit"))

    //transport
    implementation(project(":common"))
    implementation(project(":resume-ab-transport-main-openapi"))
    implementation(project(":resume-ab-transport-mapping-openapi"))
    //stubs
    implementation(project(":resume-ab-stubs"))
}