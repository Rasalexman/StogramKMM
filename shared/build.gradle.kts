plugins {
    //id("org.jetbrains.gradle.apple.applePlugin") version "212.4638.14-0.13.1"
    kotlin("multiplatform")
    //kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("io.realm.kotlin")
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

//    cocoapods {
//        summary = "Realm Kotlin Stogram shared Library"
//        homepage = "https://github.com/realm/realm-kotlin"
//        ios.deploymentTarget = "14.1"
//        osx.deploymentTarget = "11.0"
//        frameworkName = "shared"
//        podfile = project.file("../iosApp/Podfile")
//    }

    sourceSets {
        val ktorCore: String by rootProject.extra
        val ktorCio: String by rootProject.extra
        val ktorLogging: String by rootProject.extra
        val realmBase: String by rootProject.extra
        val coroutinesNative: String by rootProject.extra
        val serializationCore: String by rootProject.extra
        val datetime: String by rootProject.extra
        val commonMain by getting {
            dependencies {
                implementation(ktorCore)
                implementation(ktorCio)
                implementation(ktorLogging)
                implementation(coroutinesNative)
                implementation(serializationCore)
                implementation(datetime)
                api(realmBase)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val ktorAndroid: String by rootProject.extra
        val kodi: String by rootProject.extra
        val sresult: String by rootProject.extra
        val androidMain by getting {
            dependencies {
                api(kodi)
                api(sresult)
                implementation(ktorAndroid)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val ktorIOS: String by rootProject.extra
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(ktorIOS)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting

        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
}