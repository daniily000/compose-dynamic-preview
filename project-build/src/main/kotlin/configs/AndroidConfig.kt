package configs

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType

interface AndroidConfig : Config {
    val namespace: String
    val compileSdk: Int
    val targetSdk: Int
    val minSdk: Int
}

internal fun Project.applyAndroidConfig(config: AndroidConfig) {
    pluginManager.withPlugin("com.android.library") {
        the(LibraryExtension::class)
            .apply(::androidLibrary)
            .apply {
                publishing {
                    singleVariant("release") {
                        withSourcesJar()
                        withJavadocJar()
                    }
                }
            }
            .apply {
                namespace = config.namespace
                compileSdk = config.compileSdk
            }
    }
}

private fun Project.androidLibrary(ext: LibraryExtension) = with(ext) {
    buildTypes {
        maybeCreate("release").apply {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            freeCompilerArgs += "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        }
    }

    packaging {
        resources {
            excludes.addAll(
                listOf(
                    "META-INF/LICENSE.txt",
                    "META-INF/NOTICE.txt",
                    "META-INF/core.kotlin_module",
                    "META-INF/firebase*.kotlin_module"
                )
            )
        }
    }
}
