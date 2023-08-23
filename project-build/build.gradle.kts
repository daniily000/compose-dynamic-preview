import com.daniily.kdependency.gradle.add
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("dependencies")
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
    implementation(":dependencies")
    add(BuildDependencies)
    implementation("com.android.tools.build:gradle:8.1.0")
}
