import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    //kotlin("plugin.serialization")
    id("io.realm.kotlin")
    id("com.google.devtools.ksp")
}

val kotlinApiVersion: String by rootProject.extra
val jvmVersion: String by rootProject.extra
val realmVersion: String by rootProject.extra

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        this.apiVersion = kotlinApiVersion
        this.languageVersion = kotlinApiVersion
        this.jvmTarget = jvmVersion
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}


kotlin {

    android()
    ios()

//    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
//        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
//        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
//        else -> ::iosX64
//    }
//    iosTarget("ios") {}
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = false
        }
    }

    cocoapods {
        version = realmVersion
        summary = "Realm Kotlin Stogram shared Library"
        homepage = "https://github.com/realm/realm-kotlin"
        ios.deploymentTarget = "15.4"
        osx.deploymentTarget = "11.0"
        //podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            // Optional properties
            // Dynamic framework support
            isStatic = false
        }

    }

    sourceSets {
        val ktorCore: String by rootProject.extra
        val ktorCio: String by rootProject.extra
        val ktorLogging: String by rootProject.extra
        val realmBase: String by rootProject.extra
        val coroutinesNative: String by rootProject.extra
        //val serializationCore: String by rootProject.extra
        val datetime: String by rootProject.extra
        val kodi: String by rootProject.extra
        val sresultcore: String by rootProject.extra

        val commonMain by getting {
            dependencies {
                //implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libsjvm")))
                implementation(ktorCore)
                implementation(ktorCio)
                implementation(ktorLogging)
                implementation(coroutinesNative)
                implementation(sresultcore)
                //implementation(serializationCore)
                implementation(datetime)
                implementation(realmBase)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val ktorAndroid: String by rootProject.extra

        val sresult: String by rootProject.extra
        val androidMain by getting {
            dependencies {
                api(kodi)
                //api(sresult)
                implementation(ktorAndroid)
            }
        }
        val androidTest by getting

//        val iosMain by getting
//        val iosTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val ktorIOS: String by rootProject.extra
        val iosMain by getting {
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

        val iosTest by getting {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

val kodiksp: String by rootProject.extra
dependencies {
    add("kspCommonMainMetadata", kodiksp)
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin.targets.withType(KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["freezing"] = "disabled"
    }
}