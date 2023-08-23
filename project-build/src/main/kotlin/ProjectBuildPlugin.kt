import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType

class ProjectBuildPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // no-op
    }
}
