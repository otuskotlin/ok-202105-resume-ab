plugins {
    kotlin("jvm")
}


repositories {
    mavenCentral()
}

dependencies {
    val coroutinesVersion: String by project
    implementation(kotlin("stdlib"))


    implementation(project(":resume-ab-common-cor"))
    implementation(project(":common"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}