import com.daniily.kdependency.gradle.add

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("project-build")
    `maven-publish`
}

applyConfig(Configs.Ui)

kotlin {
//    jvmToolchain(17)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

android {
//    namespace = project.group.toString()
//
//    compileSdk = 31
//
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.ComposeCompiler
    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
}

//configure<LibraryExtension> {
//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
//        }
//    }
//}
//
//afterEvaluate {
//    configure<PublishingExtension> {
//        publications {
//            maybeCreate("maven", MavenPublication::class).from(components["release"])
//        }
//    }
//}

//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = group.toString()
//            artifactId = "ui"
//            version = version.toString()
//            from(components["release"])
//        }
//    }
//    repositories {
//        maven {
//            // change to point to your repo, e.g. http://my.org/repo
//            url = uri("$buildDir/repo")
//        }
//    }
//}

dependencies {
    add(UiDependencies)
    implementation(kotlin("reflect"))
}
