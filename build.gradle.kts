buildscript {
    apply(from="versions.gradle.kts")
    val kotlinVersion: String by rootProject.extra
    val agpVersion: String by rootProject.extra
    val realmVersion: String by rootProject.extra
    val jitpackPath: String by rootProject.extra

    repositories {
        //gradlePluginPortal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri(jitpackPath) }
        // Only required for realm-kotlin snapshots
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$agpVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("io.realm.kotlin:gradle-plugin:$realmVersion")
    }
}

allprojects {
    val jitpackPath: String by rootProject.extra
    repositories {
        google()
        mavenCentral()
        maven { url = uri(jitpackPath) }
        // Only required for realm-kotlin snapshots
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}