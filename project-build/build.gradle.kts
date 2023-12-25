plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
    mavenLocal()
}

gradlePlugin {
    plugins.register("project-build") {
        id = "project-build"
        implementationClass = "ProjectBuildPlugin"
    }
}

dependencies {
    implementation(libs.bundles.project.build)
}
