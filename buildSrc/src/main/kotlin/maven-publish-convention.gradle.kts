//plugins {
//    `maven-publish`
//}
//
//group = group.toString().replace(rootProject.name, rootProject.groupId)
//
////afterEvaluate {
////    configure<PublishingExtension> {
////        repositories {
////            val url = if (version.toString().endsWith("-SNAPSHOT")) snapshotRepo else releaseRepo
////            maven(url) {
////                setDxCredentialsId()
////            }
////        }
////    }
////}
