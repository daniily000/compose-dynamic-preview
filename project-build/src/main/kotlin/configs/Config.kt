package configs

import org.gradle.api.Project

interface Config

fun Project.applyConfig(config: Config) {
    if (config is JavaLibConfig) applyJavaLibConfig(config)
    if (config is AndroidConfig) applyAndroidConfig(config)
    if (config is PublishingConfig) applyPublishingConfig(config)
    if (config is SigningConfig) applySigningConfig(config)
}
