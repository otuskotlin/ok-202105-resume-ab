plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version
val exposedVersion: String by project
val postgresDriverVersion: String by project
val testContainersVersion: String by project

repositories {
    mavenCentral()
}

tasks {
    withType<Test> {
        environment("ok.mp.sql_drop_db", true)
        environment("ok.mp.sql_fast_migration", true)
    }
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.postgresql:postgresql:$postgresDriverVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    testImplementation("org.testcontainers:postgresql:$testContainersVersion")

    testImplementation(project(":resume-ab-repo-test"))
    implementation(project(":common"))


}