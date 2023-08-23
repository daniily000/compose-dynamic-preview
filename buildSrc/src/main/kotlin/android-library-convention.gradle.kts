//import com.android.build.gradle.LibraryExtension
//import org.gradle.api.publish.maven.MavenPublication
//import org.gradle.kotlin.dsl.maybeCreate
//
//plugins {
//    id("com.android.library")
//    id("android-convention")
//}
//
//version = project.versionName
//
//configure<LibraryExtension> {
//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
//        }
//    }
//}
//
////afterEvaluate {
////    configure<PublishingExtension> {
////        publications {
////            maybeCreate("maven", MavenPublication::class).from(components["release"])
////        }
////    }
////}
