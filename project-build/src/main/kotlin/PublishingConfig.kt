import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.the

interface PublishingConfig : Config {
    val name: String
    val group: String
    val version: String
}

internal fun Project.applyPublishingConfig(config: PublishingConfig) {
    pluginManager.withPlugin("maven-publish") {
        the(PublishingExtension::class)
            .apply { publishing(this, config) }
    }
}

private fun Project.publishing(
    ext: PublishingExtension,
    config: PublishingConfig,
) = with(ext) {
    publications {
        maybeCreate("release", MavenPublication::class).apply {
            groupId = config.group
            artifactId = config.name
            version = config.version
        }
        repositories {
            maven {
                url = uri("$buildDir/repo")
            }
        }
    }

    afterEvaluate {
        pluginManager.withPlugin("maven-publish") {
            the(PublishingExtension::class)
                .publications
                .maybeCreate("release", MavenPublication::class)
                .from(components[config.publicationComponent])
        }
    }
}
