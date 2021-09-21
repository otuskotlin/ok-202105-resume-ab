plugins {
    kotlin("jvm")
}


dependencies {
    implementation(kotlin("stdlib"))

    //transport
    implementation(project(":common"))
    implementation(project(":resume-ab-transport-main-openapi"))
    implementation(project(":resume-ab-transport-mapping-openapi"))
    implementation(project(":resume-ab-logics"))
    implementation(project(":resume-ab-stubs"))
}