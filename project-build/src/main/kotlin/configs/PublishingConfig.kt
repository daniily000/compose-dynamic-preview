package configs

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.credentials
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.the
import java.net.URI

interface PublishingConfig : Config {
    val name: String
    val group: String
    val version: String
    val packaging: String
    val description: String
    val authors: List<Author>
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

    afterEvaluate {
        publications {
            maybeCreate("release", MavenPublication::class).apply {
                groupId = config.group
                artifactId = config.name
                version = config.version

                from(components[config.publicationComponent])

                pom {
                    name = config.name
                    description = config.description
                    packaging = config.packaging
                    licenses {
                        license {

                        }
                    }
                    authors(config.authors)
                }
            }
        }
    }
}

private fun MavenPomDeveloperSpec.author(author: Author) {
    developer {
        name = author.name
        email = author.email
    }
}

private fun MavenPom.authors(authors: List<Author>) {
    authors.forEach {
        developers {
            author(it)
        }
    }
}

private val Config.publicationComponent: String
    get() = when (this) {
        is AndroidConfig -> "release"
        is JavaLibConfig -> "java"
        else -> "kotlin"
    }
