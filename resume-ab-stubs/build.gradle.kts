plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    // transport models
    implementation(project(":common"))

    testImplementation(kotlin("test-junit"))
}