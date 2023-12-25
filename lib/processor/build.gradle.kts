plugins {
    id("kotlin")
    `java-library`
    `maven-publish`
    id("project-build")
}

applyConfig(Configs.Processor)

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation(project(":lib:api"))
    implementation(libs.bundles.project.processor)
}
