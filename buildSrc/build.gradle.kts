import java.util.Properties

plugins {
    `kotlin-dsl`
    id("dependencies")
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}")
    implementation("com.android.tools.build:gradle:${Versions.AndroidGradlePlugin}")
//    val kspVersion = "${versions["kotlin_gradle_plugin"]}-${versions["ksp_only_plugin"]}"
//    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$kspVersion")
}
