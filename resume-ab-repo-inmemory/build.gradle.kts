plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

val ehcacheVersion: String by project
val coroutinesVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.ehcache:ehcache:$ehcacheVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    //common
    implementation(project(":common"))
    implementation(project(":resume-ab-repo-test"))
}