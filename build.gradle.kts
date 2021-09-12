
plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "ru.otus.otuskotlin.resume"
version = "1.0.0"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}