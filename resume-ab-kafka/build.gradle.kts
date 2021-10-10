val kafkaVersion: String by project
val coroutinesVersion: String by project
val jacksonVersion: String by project

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("ru.otus.otuskotlin.resume.kafka.MainKt")
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    //transport
    implementation(project(":common"))
    implementation(project(":resume-ab-transport-main-openapi"))
    implementation(project(":resume-ab-transport-mapping-openapi"))
    //service
    implementation(project(":resume-ab-service-openapi"))
    //
    implementation(project(":resume-ab-logics"))
    //stubs
    implementation(project(":resume-ab-stubs"))

    testImplementation(kotlin("test-junit"))
}