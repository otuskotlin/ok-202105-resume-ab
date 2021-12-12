plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

val coroutinesVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(kotlin("stdlib"))
    //transport
    implementation(project(":common"))
    implementation(project(":resume-ab-common-cor"))
    //validation
    implementation(project(":resume-ab-validation"))
    //stubs
    implementation(project(":resume-ab-stubs"))
    //Db
    implementation(project(":resume-ab-repo-inmemory"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}