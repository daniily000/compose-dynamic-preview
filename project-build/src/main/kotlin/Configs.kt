import configs.AndroidConfig
import configs.Author
import configs.JavaLibConfig
import configs.PluginConfig
import configs.PublishingConfig
import configs.SigningConfig

object Configs {

    const val platformVersion = "0.1.3"

    abstract class SharedConfig : JavaLibConfig {
        override val group: String = "com.daniily.preview"
        override val version: String = platformVersion
    }

    object Api : SharedConfig(), PublishingConfig, SigningConfig {
        override val name: String = "api"
        override val packaging: String = "jar"
        override val description: String = "API for Compose Dynamic Preview"
        override val authors: List<Author> = listOf(Authors.DaniilY)
    }

    object Processor : SharedConfig(), PublishingConfig, SigningConfig {
        override val name: String = "processor"
        override val packaging: String = "jar"
        override val description: String = "KSP processor for Compose Dynamic Preview"
        override val authors: List<Author> = listOf(Authors.DaniilY)
    }

    object Ui : SharedConfig(), AndroidConfig, PublishingConfig, SigningConfig {
        override val name: String = "ui"
        override val packaging: String = "aar"
        override val description: String = "Pre-build UI elements for Compose Dynamic Preview"
        override val authors: List<Author> = listOf(Authors.DaniilY)
        override val namespace: String = group
        override val compileSdk = 34
        override val targetSdk = 34
        override val minSdk = 24
    }

    object Plugin : SharedConfig(), PluginConfig {
        override val name: String = "ComposeDynamicPreview"
    }
}
