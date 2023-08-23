plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "DependenciesPlugin"
    }
}

repositories {
    google()
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.daniily.kdependency:gradle:0.0.1")
}
