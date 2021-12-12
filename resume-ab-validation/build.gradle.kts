plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))


    implementation(project(":resume-ab-common-cor"))
    implementation(project(":common"))

    testImplementation(kotlin("test"))
//    testImplementation(kotlin("test-junit"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}