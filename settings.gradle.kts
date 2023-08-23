rootProject.name = "ComposeDynamicPreview"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}


includeBuild("dependencies")
includeBuild("project-build")

include(":lib")
include(":lib:api")
include(":lib:processor")
include(":lib:ui")
include(":plugin")
