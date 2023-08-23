import com.daniily.kdependency.KDependencyScope
import org.gradle.api.Plugin
import org.gradle.api.Project

class DependenciesPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        // no-op
    }
}

typealias KDependencyBlock = KDependencyScope.() -> Unit
