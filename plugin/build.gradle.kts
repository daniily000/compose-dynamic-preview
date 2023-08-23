import configs.applyConfig

plugins {
    alias(libs.plugins.intellij)
    alias(libs.plugins.kotlin.jvm)
    id("project-build")
}

applyConfig(Configs.Plugin)

repositories {
    mavenCentral()
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
        untilBuild.set("233")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
