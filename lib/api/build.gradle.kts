plugins {
    id("kotlin")
    `java-library`
    `maven-publish`
    id("project-build")
}

applyConfig(Configs.Api)

//group = "com.daniily.preview"
//version = LibVersion.Api
//
//java {
//    withSourcesJar()
//    withJavadocJar()
//}
//
//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            groupId = group.toString()
//            artifactId = "api"
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
