plugins {
    kotlin("multiplatform") version "1.6.21" apply false
    kotlin("native.cocoapods") version "1.6.21" apply false
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
    id("com.android.library") version "7.2.1" apply false
}

buildscript {
    apply(from="versions.gradle.kts")
//    val kotlinVersion: String by rootProject.extra
//    val agpVersion: String by rootProject.extra
    val realmVersion: String by rootProject.extra
//    val jitpackPath: String by rootProject.extra

//    repositories {
//        gradlePluginPortal()
//        google()
//        mavenCentral()
//        maven {
//            url = uri("https://plugins.gradle.org/m2/")
//        }
//        maven { url = uri(jitpackPath) }
//        // Only required for realm-kotlin snapshots
//        maven("https://oss.sonatype.org/content/repositories/snapshots")
//    }
    dependencies {
        //classpath("com.android.tools.build:gradle:$agpVersion")
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        //classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("io.realm.kotlin:gradle-plugin:$realmVersion")
    }
}
//allprojects {
//    group = "io.realm.sample"
//    version = "0.11.1"
//}
//
//allprojects {
//    val jitpackPath: String by rootProject.extra
//    repositories {
//        google()
//        mavenCentral()
//        maven {
//            url = uri("https://plugins.gradle.org/m2/")
//        }
//        maven { url = uri(jitpackPath) }
//        // Only required for realm-kotlin snapshots
//        maven("https://oss.sonatype.org/content/repositories/snapshots")
//    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}