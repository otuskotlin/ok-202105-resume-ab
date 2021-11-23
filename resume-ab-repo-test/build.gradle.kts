plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

val coroutinesVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":common"))

    api(kotlin("test-junit5"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}