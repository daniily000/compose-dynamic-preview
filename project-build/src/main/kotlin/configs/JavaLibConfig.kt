package configs

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.the

interface JavaLibConfig : Config {
    val name: String
    val group: String
    val version: String
}

internal fun Project.applyJavaLibConfig(config: JavaLibConfig) {
    group = config.group
    version = config.version

    pluginManager.withPlugin("java-library") {
        the(JavaPluginExtension::class).apply {
            withSourcesJar()
            withJavadocJar()
        }
    }
}
