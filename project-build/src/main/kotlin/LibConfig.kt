import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.the

interface LibConfig : Config {
    val name: String
    val group: String
    val version: String
}

internal fun Project.applyLibConfig(config: LibConfig) {
    group = config.group
    version = config.version

    afterEvaluate {
        pluginManager.withPlugin("java-library") {
            the(JavaPluginExtension::class).apply {
                withSourcesJar()
                withJavadocJar()
            }
        }
    }
}
