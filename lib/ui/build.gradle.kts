plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("project-build")
    `maven-publish`
}

applyConfig(Configs.Ui)

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

android {

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.project.ui)
    implementation(kotlin("reflect"))
}
