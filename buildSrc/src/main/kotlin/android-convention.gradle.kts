//import com.android.build.gradle.AppExtension
//import com.android.build.gradle.BaseExtension
//import com.android.build.gradle.LibraryExtension
//import com.android.builder.core.DefaultApiVersion
//
//plugins {
//    id("kotlin-android")
//}
//
////fun applyConfig(config: Config) {
////
////}
////
////private fun applyAndroidConfig(config: Android)  {
////
////}
//
//pluginManager.withPlugin("com.android.library") {
//    the(LibraryExtension::class)
//        .apply {
//            lint { abortOnError = false }
//        }
//        .commonAndroidModule()
//}
//
//pluginManager.withPlugin("com.android.application") {
//    the(AppExtension::class)
//        .apply { lintOptions.isAbortOnError = false }
//        .commonAndroidModule()
//}
//
//fun BaseExtension.commonAndroidModule() {
//    buildTypes {
//        maybeCreate("release").apply {
//            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//            consumerProguardFiles("proguard-rules.pro")
//        }
//    }
//
//
//    compileSdkVersion(31/*project.compileSdkVersion*/)
//
//    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions {
//            jvmTarget = JavaVersion.VERSION_1_8.toString()
//            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
//            freeCompilerArgs += "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
//        }
//    }
//
//    defaultConfig {
//        minSdkVersion = DefaultApiVersion(project.minSdkVersion)
//        targetSdkVersion = DefaultApiVersion(project.targetSdkVersion)
//        versionName = project.versionName
//        versionCode = project.versionCode
//    }
//
//    packagingOptions {
//        resources {
//            excludes.addAll(
//                listOf(
//                    "META-INF/LICENSE.txt",
//                    "META-INF/NOTICE.txt",
//                    "META-INF/core.kotlin_module",
//                    "META-INF/firebase*.kotlin_module"
//                )
//            )
//        }
//    }
//
//    project.dependencies {
//        "coreLibraryDesugaring"("com.android.tools:desugar_jdk_libs:1.1.5")
//        "implementation"(kotlin("stdlib-jdk8", "1.9.0"))
//    }
//}
