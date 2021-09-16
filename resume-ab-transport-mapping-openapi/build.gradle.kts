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

    implementation(project(":common"))
    implementation(project(":resume-ab-transport-main-mp"))

    testImplementation(kotlin("test"))
}