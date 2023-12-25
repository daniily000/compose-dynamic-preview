import org.gradle.api.Project

object Configs {

    const val platformVersion = "0.1.2"

    abstract class SharedConfig : LibConfig {
        override val group: String = "com.daniily.preview"
        override val version: String = platformVersion
    }

    object Api : SharedConfig(), PublishingConfig {
        override val name: String = "api"
    }

    object Processor : SharedConfig(), PublishingConfig {
        override val name: String = "processor"
    }

    object Ui : SharedConfig(), AndroidConfig, PublishingConfig {
        override val name: String = "ui"
        override val namespace: String = group
        override val compileSdk = 34
        override val targetSdk = 34
        override val minSdk = 24
    }

    object Plugin : SharedConfig(), PluginConfig {
        override val name: String = "ComposeDynamicPreview"
    }
}

interface Config

fun Project.applyConfig(config: Config) {
    if (config is LibConfig) applyLibConfig(config)
    if (config is AndroidConfig) applyAndroidConfig(config)
    if (config is PublishingConfig) applyPublishingConfig(config)
}

internal val Config.publicationComponent: String
    get() = when (this) {
        is AndroidConfig -> "release"
        is LibConfig -> "kotlin"
        else -> "kotlin"
    }
