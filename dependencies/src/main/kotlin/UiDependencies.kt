import com.daniily.kdependency.KDependencies
import com.daniily.kdependency.gradle.implementation
import com.daniily.kdependency.gradle.platform

val Compose: KDependencyBlock = {
    implementation {
        platform {
            +artifact(group = "androidx.compose", name = "compose-bom", version = Versions.Compose)
        }
        +artifact(group = "androidx.compose.foundation", name = "foundation")
        +artifact(group = "androidx.compose.ui", name = "ui-tooling-preview")
        +artifact(group = "androidx.compose.material", name = "material")
    }

//    debugImplementation {
//        +artifact(group = "androidx.compose.ui", name = "ui-tooling")
//    }
}

val ComposeNoPlatform: KDependencyBlock = {
    implementation {
        platform {
//            +artifact(group = "androidx.compose", name = "compose-bom", version = Versions.Compose)
        }
        +artifact(group = "androidx.compose.foundation", name = "foundation", version = Versions.Compose)
        +artifact(group = "androidx.compose.ui", name = "ui-tooling-preview", version = Versions.Compose)
        +artifact(group = "androidx.compose.material", name = "material", version = Versions.Compose)
    }

//    debugImplementation {
//        +artifact(group = "androidx.compose.ui", name = "ui-tooling")
//    }
}

object UiDependencies : KDependencies by KDependencies({
//    Compose()
    ComposeNoPlatform()
})
