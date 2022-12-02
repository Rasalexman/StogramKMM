plugins {
    kotlin("multiplatform") version "1.7.20" apply false
    kotlin("native.cocoapods") version "1.7.20" apply false
//    id("com.google.devtools.ksp") version "1.7.20-1.0.7"
    id("com.android.library") version "7.3.1" apply false
}

buildscript {
    apply(from="versions.gradle.kts")
//    val kotlinVersion: String by rootProject.extra
//    val agpVersion: String by rootProject.extra
    val realmVersion: String by rootProject.extra
    val hiltVersion: String by rootProject.extra
    val composeNavigationVersion: String by rootProject.extra
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
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$composeNavigationVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
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