import com.daniily.kdependency.KDependencies
import com.daniily.kdependency.KDependencyScope
import com.daniily.kdependency.gradle.add
import com.daniily.kdependency.gradle.api
import com.daniily.kdependency.gradle.implementation
import com.daniily.kdependency.gradle.platform
import com.daniily.kdependency.gradle.project
import org.gradle.kotlin.dsl.DependencyHandlerScope


val KSP: KDependencyBlock = {
    +artifact(
        group = "com.google.devtools.ksp",
        name = "symbol-processing-api",
        version = "${Versions.Kotlin}-${Versions.KSP}"
    )
}

val ComposeDynamicPreviewApi: KDependencyBlock = { +artifact(":lib:api") }

val KotlinPoet: KDependencyBlock = {
    group(
        name = "com.squareup",
        version = Versions.KotlinPoet
    ) {
        +artifact("kotlinpoet")
        +artifact("kotlinpoet-ksp")
    }
}

object ProcessorDependencies : KDependencies by KDependencies({

    implementation {
        KSP()
        project {
            ComposeDynamicPreviewApi()
        }
    }
    api {
        KotlinPoet()
    }
})
