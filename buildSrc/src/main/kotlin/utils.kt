import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.apply
import java.util.Properties

//val Project.versions: Map<String, String> get() {
//    return file("$rootDir/versions.properties")
//        .inputStream()
//        .use { Properties().apply { load(it) } }
//        .entries
//        .associate { it.key.toString() to it.value.toString() }
//}

/**
 * Returns base group name for all artifacts, which are supposed to be published.
 * Usually being set in gradle.properties file
 */
val Project.groupId: String get() {
    return rootProject.ext["groupId"]?.toString() ?: error("No property groupId is set in gradle.properties file")
}

/**
 * Returns URL of release repo, where all artifact are going to be published in case of app release.
 * Usually being set in gradle.properties file
 */
val Project.releaseRepo: String get() {
    return rootProject.ext["releaseRepo"]?.toString()
        ?: error("No property releaseRepo is set in gradle.properties file")
}

/**
 * Returns URL of snapshot repo, where all artifact are going to be published in case of app snapshot publish.
 * Usually being set in gradle.properties file
 */
val Project.snapshotRepo: String get() {
    return rootProject.ext["snapshotRepo"]?.toString()
        ?: error("No property snapshotRepo is set in gradle.properties file")
}

/**
 * Returns version name of all artifacts, which are going to be published or installed (including android app).
 * You can specify it in gradle.properties file or through CLI arg:
 * `./gradlew assembleRelease -PversionName=1.2.3-SNAPSHOT`
 */
val Project.versionName: String get() {
    return findProperty("versionName")?.toString() ?: "dev-SNAPSHOT"
}

/**
 * Returns version code of android app, which is going to be built or installed.
 * You can specify it in gradle.properties file or through CLI arg:
 * `./gradlew assembleRelease -PversionCode=112233`
 */
val Project.versionCode: Int get() {
    return project.findProperty("versionCode")?.toString()?.toInt() ?: 1
}

/**
 * Returns compileSdkVersion.
 * Usually being set in gradle.properties file
 */
val Project.compileSdkVersion: Int get() {
    return project.findProperty("compileSdkVersion")?.toString()?.toInt()
        ?: error("No property compileSdkVersion is set in gradle.properties file")
}

/**
 * Returns minSdkVersion.
 * Usually being set in gradle.properties file
 */
val Project.minSdkVersion: Int get() {
    return project.findProperty("minSdkVersion")?.toString()?.toInt()
        ?: error("No property minSdkVersion is set in gradle.properties file")
}

/**
 * Returns targetSdkVersion.
 * Usually being set in gradle.properties file
 */
val Project.targetSdkVersion: Int get() {
    return project.findProperty("targetSdkVersion")?.toString()?.toInt()
        ?: error("No property targetSdkVersion is set in gradle.properties file")
}

private val Project.ext: ExtraPropertiesExtension get() {
    return extensions.getByName("ext") as ExtraPropertiesExtension
}
