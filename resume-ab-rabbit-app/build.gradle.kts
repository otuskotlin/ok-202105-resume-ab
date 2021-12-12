val rabbitVersion: String by project
val jacksonVersion: String by project
val coroutinesVersion: String by project
val logbackVersion: String by project
val testContainersVersion: String by project

plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

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
    //Db
    implementation(project(":resume-ab-repo-inmemory"))

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test"))
}