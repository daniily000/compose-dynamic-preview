import com.daniily.kdependency.gradle.add

plugins {
    id("kotlin")
    `java-library`
    `maven-publish`
    id("project-build")
}

applyConfig(Configs.Processor)

//group = "com.daniily.preview"
//version = LibVersion.Processor
//
java {
    withSourcesJar()
    withJavadocJar()
}
//
//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            groupId = group.toString()
//            artifactId = "processor"
//            version = version.toString()
//            from(components["kotlin"])
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
    add(ProcessorDependencies)
}
