plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
}

val kotlinApiVersion: String by rootProject.extra
val jvmVersion: String by rootProject.extra

android {
    val composeVersion: String by rootProject.extra
    val appVersion: String by rootProject.extra
    val minSdkVersion: Int by rootProject.extra
    val buildSdkVersion: Int by rootProject.extra

    compileSdk = buildSdkVersion
    defaultConfig {
        applicationId = "ru.stogram.android"
        minSdk = minSdkVersion
        targetSdk = buildSdkVersion
        versionCode = 1
        versionName = appVersion
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        languageVersion = kotlinApiVersion
        apiVersion = kotlinApiVersion
        jvmTarget = jvmVersion
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    kotlin {
        sourceSets.release {
            kotlin.srcDirs("build/generated/ksp/release/kotlin")
        }
        sourceSets.debug {
            kotlin.srcDirs("build/generated/ksp/debug/kotlin")
        }
    }
}

dependencies {
    val composeUI: String by rootProject.extra
    val composeMaterial: String by rootProject.extra
    val composePreview: String by rootProject.extra
    val composeActivity: String by rootProject.extra
    val composeNavigation: String by rootProject.extra
    val composeFonts: String by rootProject.extra
    val composeIcons: String by rootProject.extra

    val companistFlowLayout: String by rootProject.extra
    val companistInsetsUI: String by rootProject.extra
    val companistNavigation: String by rootProject.extra
    val companistSwipeRefresh: String by rootProject.extra
    val companistPager: String by rootProject.extra
    val companistPagerIndicators: String by rootProject.extra

    val core: String by rootProject.extra
    val lifecycleVM: String by rootProject.extra
    val coroutinesAndroid: String by rootProject.extra
    val realmBase: String by rootProject.extra

    val leakCanary: String by rootProject.extra
    val kodiksp: String by rootProject.extra
    val timber: String by rootProject.extra
    val coil: String by rootProject.extra
    val swiperefreshlayout: String by rootProject.extra

    implementation(project(":shared"))
    implementation(core)
    implementation(lifecycleVM)
    implementation(coroutinesAndroid)
    implementation(timber)
    implementation(coil)
    compileOnly(realmBase)

    implementation(composeUI)
    implementation(swiperefreshlayout)
    implementation(composeMaterial)
    implementation(composePreview)
    implementation(composeActivity)
    implementation(composeNavigation)
    implementation(composeFonts)
    implementation(composeIcons)

    implementation(companistFlowLayout)
    implementation(companistInsetsUI)
    implementation(companistNavigation)
    implementation(companistSwipeRefresh)
    implementation(companistPager)
    implementation(companistPagerIndicators)

    ksp(kodiksp)

    debugImplementation(leakCanary)
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
}