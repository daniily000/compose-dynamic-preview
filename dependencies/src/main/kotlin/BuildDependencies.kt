import com.daniily.kdependency.KDependencies
import com.daniily.kdependency.gradle.add
import org.gradle.kotlin.dsl.DependencyHandlerScope


object BuildDependencies : KDependencies by KDependencies({

    val KotlinGradlePlugin = artifact(
        group = "org.jetbrains.kotlin",
        name = "kotlin-gradle-plugin",
        version = Versions.Kotlin
    )

    val AndroidGradlePlugin = artifact(
        group = "com.android.tools.build",
        name = "gradle",
        version = Versions.AndroidGradlePlugin
    )

    +KotlinGradlePlugin
    +AndroidGradlePlugin
})
