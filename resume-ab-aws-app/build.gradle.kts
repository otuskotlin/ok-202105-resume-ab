import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.gradle.node.npm.task.NpxTask

val jacksonVersion: String by project
val lambdaCoreVersion: String by project
val lambdaLog4jVersion: String by project
val lambdaEventsVersion: String by project
val coroutinesVersion: String by project

plugins {
    kotlin("jvm")
    id("com.github.node-gradle.node") version "3.0.1"
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    //transport
    implementation(project(":common"))
    implementation(project(":resume-ab-transport-main-openapi"))
    implementation(project(":resume-ab-transport-mapping-openapi"))
    //stubs
    implementation(project(":resume-ab-stubs"))
    //service
    implementation(project(":resume-ab-service-openapi"))
    //Db
    implementation(project(":resume-ab-repo-inmemory"))
//logics
    implementation(project(":resume-ab-logics"))

    implementation("com.amazonaws:aws-lambda-java-core:$lambdaCoreVersion")
    implementation("com.amazonaws:aws-lambda-java-log4j:$lambdaLog4jVersion")
    implementation("com.amazonaws:aws-lambda-java-events:$lambdaEventsVersion")

    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

val buildZip by tasks.creating(Zip::class) {
    archiveBaseName.set("functions")
    from(tasks.named("compileKotlin"))
    from(tasks.named("processResources"))
    into("lib") {
        from(configurations.runtimeClasspath)
    }
}

val build by tasks.getting {
    dependsOn(buildZip)
}

val awsDeploy by tasks.creating(NpxTask::class) {
    dependsOn(build, "npmInstall")
    command.set("serverless")
    args.set(listOf("deploy"))
    environment.put("ARTIFACT", buildZip.archiveFile.map { it.asFile.absolutePath })
}