package configs

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.signing.SigningExtension

interface SigningConfig : Config

internal fun Project.applySigningConfig(config: SigningConfig) {
    pluginManager.withPlugin("signing") {
        val publications = the(PublishingExtension::class).publications
        the(SigningExtension::class).sign(publications)
    }
}
