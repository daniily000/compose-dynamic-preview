plugins {
    id("org.jetbrains.intellij") version "1.11.0"
    kotlin("jvm")
    id("project-build")
}

applyConfig(Configs.Plugin)

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    type.set("AI")
    version.set("2021.2.1.14")
    plugins.set(listOf("android"))
}

tasks {
    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("232.*")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
