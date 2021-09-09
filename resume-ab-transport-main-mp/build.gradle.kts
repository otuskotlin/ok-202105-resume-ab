plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.openapi.generator")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted.
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    js{
        nodejs()
        browser {
            testTask {
                useKarma {
                    useFirefox()
                }
            }
        }
    }
    jvm{}

    val generatedSourcesDir = "$buildDir/generated"

    sourceSets {
        val serializationVersion: String by project
        val commonMain by getting {
            kotlin.srcDirs("$generatedSourcesDir/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }

    /**
     * Настраиваем генерацию здесь
     */
    openApiGenerate {
        val openapiGroup = "${rootProject.group}.mp"
        library.set("multiplatform")
        outputDir.set(generatedSourcesDir)
        generatorName.set("kotlin") // Это и есть активный генератор
        packageName.set(openapiGroup)
        apiPackage.set("$openapiGroup.api")
        modelPackage.set("$openapiGroup.models")
        invokerPackage.set("$openapiGroup.invoker")
        inputSpec.set("$rootDir/specs/spec-resume-ab-api-v0.0.1.yaml")

        /**
         * Здесь указываем, что нам нужны только модели, все остальное не нужно
         */
        globalProperties.apply {
            put("models", "")
            put("modelDocs", "false")
        }

        /**
         * Настройка дополнительных параметров из документации по генератору
         * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
         */
        configOptions.set(mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "collectionType" to "list"
        ))
    }

    tasks {
        compileKotlinMetadata {
            dependsOn(openApiGenerate)
        }
    }
}